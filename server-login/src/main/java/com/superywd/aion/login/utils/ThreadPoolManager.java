package com.superywd.aion.login.utils;

import com.superywd.aion.commons.utils.concurrent.MyRejectedExecutionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池管理类（技术体现^-^）
 * @author: 迷宫的中心
 * @date: 2018/11/14 22:56
 */

public class ThreadPoolManager {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolManager.class);


    private final ScheduledThreadPoolExecutor scheduledPool;

    /**
     * 瞬时任务运行线程池
     */
    private final ThreadPoolExecutor instantPool;

    /**
     * 长时任务运行线程池
     */
    private final ThreadPoolExecutor longRunningPool;


    private ThreadPoolManager(){
        // 可用处理器核心数 * 4 + 2
        int threadPoolSize = 2 +  Runtime.getRuntime().availableProcessors() * 4;
        int instantPoolSize = Math.max(1,threadPoolSize/3);

        //TODO: 线程池的使用，这块我需要去了解一下
        scheduledPool = new ScheduledThreadPoolExecutor(threadPoolSize - instantPoolSize);
        scheduledPool.setRejectedExecutionHandler(new MyRejectedExecutionHandler());
        // 启动所有的线程，让他们开始工作
        scheduledPool.prestartAllCoreThreads();
    }



}
