package com.superywd.aion.login.utils;

import com.superywd.aion.commons.utils.concurrent.MyRejectedExecutionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

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

        // 线程池大小 = 可用处理器核心数 * 4 + 2
        int threadPoolSize = 2 + Runtime.getRuntime().availableProcessors() * 4;
        int instantPoolSize = Math.max(1,threadPoolSize/3);

        //TODO: 线程池的使用
        scheduledPool = new ScheduledThreadPoolExecutor(threadPoolSize - instantPoolSize,new ScheduledPoolThreadFactory());
        scheduledPool.setRejectedExecutionHandler(new MyRejectedExecutionHandler());
        // 启动所有的线程，让他们开始工作
        scheduledPool.prestartAllCoreThreads();

        instantPool = new ThreadPoolExecutor(instantPoolSize,instantPoolSize,0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100000),new InstantPoolThreadFactory());
        instantPool.setRejectedExecutionHandler(new MyRejectedExecutionHandler());
        instantPool.prestartAllCoreThreads();

        longRunningPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),new LongRunningPoolThreadFactory());
        longRunningPool.setRejectedExecutionHandler(new MyRejectedExecutionHandler());
        longRunningPool.prestartAllCoreThreads();

    }

}
