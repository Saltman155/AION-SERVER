package com.superywd.aion.login.utils.cron;

import com.superywd.aion.commons.service.cron.RunnableRunner;
import com.superywd.aion.login.utils.ThreadPoolManager;

/**
 * 定时任务调度类
 * @author: saltman155
 * @date: 2018/11/12 23:44
 */

public class ThreadPoolManagerRunnableRunner extends RunnableRunner {


    @Override
    public void executeRunnable(Runnable r) {
        ThreadPoolManager.getInstance();
    }

    @Override
    public void executeLongRunningRunnable(Runnable r) {
        ThreadPoolManager.getInstance();
    }
}
