package com.aionstar.commons.service.cron;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 *
 * @author: saltman155
 * @date: 2018/11/4 13:57
 */

public abstract class RunnableRunner implements Job {

    public static final String KEY_RUNNABLE_OBJECT = "cronservice.scheduled.runnable.instance";

    public static final String KEY_PROPERTY_IS_LONG_RUNNING_TASK  = "cronservice.scheduled.runnable.islognrunning";

    public static final String KEY_CRON_EXPRESSION = "cronservice.scheduled.runnable.cronexpression";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jdm = jobExecutionContext.getJobDetail().getJobDataMap();
        Runnable r = (Runnable)jdm.get(KEY_RUNNABLE_OBJECT);
        boolean longRunning = jdm.getBoolean(KEY_PROPERTY_IS_LONG_RUNNING_TASK);
        if(longRunning){
            executeLongRunningRunnable(r);
        } else {
            executeRunnable(r);
        }
    }

    /**
     *
     * @param r
     */
    public abstract void executeRunnable(Runnable r);

    /**
     *
     * @param r
     */
    public abstract void executeLongRunningRunnable(Runnable r);
}
