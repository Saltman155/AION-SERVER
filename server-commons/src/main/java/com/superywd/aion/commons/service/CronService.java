package com.superywd.aion.commons.service;

import com.superywd.aion.commons.service.cron.CronServiceException;
import com.superywd.aion.commons.service.cron.RunnableRunner;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 *  定时任务调度管理类（quartz管理）
 * @author: 迷宫的中心
 * @date: 2018/11/4 13:19
 */

public class CronService {

    private static final Logger logger = LoggerFactory.getLogger(CronService.class);

    private static CronService instance;

    private Scheduler scheduler;

    private Class<? extends RunnableRunner> runnableRunner;

    private CronService() {
    }

    /**
     * 单例模式 获取实例
     * @return
     */
    public static CronService getInstance() {
        return instance;
    }

    public static synchronized void initSingleton(Class<? extends RunnableRunner> runnableRunner) {
        if (instance != null) {
            throw new CronServiceException("CronService已被初始化...");
        }
        CronService cs = new CronService();
        cs.init(runnableRunner);
        instance = cs;
    }

    public synchronized void init(Class<? extends RunnableRunner> runnableRunner) {
        if (scheduler != null) {
            return;
        }
        if (runnableRunner == null) {
            throw new CronServiceException("RunnableRunner class must be defined");
        }
        this.runnableRunner = runnableRunner;
        Properties properties = new Properties();
        properties.setProperty("org.quartz.threadPool.threadCount", "1");

        try {
            scheduler = new StdSchedulerFactory(properties).getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            throw new CronServiceException("初始化CronService失败...", e);
        }
    }

    public void shutdown() {
        Scheduler localScheduler;
        // 这里做了一个小小的优化，当一个线程来终止了定时任务管理器时，
        // 将scheduler置空，其余的终止线程检测到后就会推出
        synchronized (this) {
            if (scheduler == null) {
                return;
            }
            localScheduler = scheduler;
            scheduler = null;
            runnableRunner = null;
        }
        try {
            localScheduler.shutdown(false);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            logger.error("终止CronService失败...");
        }
    }

    public void schedule(Runnable r, String cronExpression) {
        schedule(r, cronExpression, false);
    }

    public void schedule(Runnable r, String cronExpression, boolean longRunning) {
        try {
            JobDataMap jdm = new JobDataMap();
            jdm.put(RunnableRunner.KEY_RUNNABLE_OBJECT, r);
            jdm.put(RunnableRunner.KEY_PROPERTY_IS_LONG_RUNNING_TASK, longRunning);
            jdm.put(RunnableRunner.KEY_CRON_EXPRESSION, cronExpression);
            String jobId = "Started at ms" + System.currentTimeMillis() + "; ns" + System.nanoTime();
            JobKey jobKey = new JobKey("JobKey:" + jobId);
            JobDetail jobDetail = JobBuilder.newJob(runnableRunner).usingJobData(jdm).withIdentity(jobKey).build();
            CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger trigger = TriggerBuilder.newTrigger().withSchedule(csb).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new CronServiceException("任务启动失败...", e);
        }
    }

    public void cancel(Runnable r) {
        Map<Runnable, JobDetail> map = getRunnableMap();
        JobDetail jd = map.get(r);
        cancel(jd);
    }

    public void cancel(JobDetail jd) {
        if (jd == null) {
            return;
        }
        if (jd.getKey() == null) {
            throw new CronServiceException("待取消的任务不存在id...");
        }
        try {
            scheduler.deleteJob(jd.getKey());
        } catch (SchedulerException e) {
            throw new CronServiceException("取消任务失败...", e);
        }
    }

    public Map<Runnable, JobDetail> getRunnableMap() {
        Collection<JobDetail> jobDetails = getJobDetails();
        if (jobDetails == null ||jobDetails.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Runnable, JobDetail> result = new HashMap<>();
        for (JobDetail jd : jobDetails) {
            if (jd.getJobDataMap()==null || jd.getJobDataMap().isEmpty()) {
                continue;
            }
            if (jd.getJobDataMap().containsKey(RunnableRunner.KEY_RUNNABLE_OBJECT)) {
                result.put((Runnable) jd.getJobDataMap().get(RunnableRunner.KEY_RUNNABLE_OBJECT), jd);
            }
        }
        return Collections.unmodifiableMap(result);
    }

    public List<? extends Trigger> getJobTriggers(JobDetail jd) {
        return getJobTriggers(jd.getKey());
    }

    public List<? extends Trigger> getJobTriggers(JobKey jk) {
        if (scheduler == null) {
            return Collections.emptyList();
        }
        try {
            return scheduler.getTriggersOfJob(jk);
        } catch (SchedulerException e) {
            throw new CronServiceException("无法根据JobKey " + jk + " 获取对应的trigger", e);
        }
    }

    protected Collection<JobDetail> getJobDetails() {
        if (scheduler == null) {
            return Collections.emptySet();
        }
        try {
            Set<JobKey> keys = scheduler.getJobKeys(null);
            if (keys == null || keys.isEmpty()) {
                return Collections.emptySet();
            }
            Set<JobDetail> result = new HashSet<>(keys.size());
            for (JobKey jk : keys) {
                result.add(scheduler.getJobDetail(jk));
            }
            return result;
        } catch (Exception e) {
            logger.error("获取所有定时任务失败...");
            throw new CronServiceException(e.getMessage(), e);
        }
    }


}
