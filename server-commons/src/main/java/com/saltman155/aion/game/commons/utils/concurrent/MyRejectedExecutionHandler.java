package com.saltman155.aion.game.commons.utils.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池溢出处理类
 * @author: saltman155
 * @date: 2018/11/14 23:30
 */

public class MyRejectedExecutionHandler implements RejectedExecutionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // 如果原来的线程池已经凉了，那这个线程也没有执行的必要了
        if(executor.isShutdown()){
            return;
        }
        logger.warn(executor+" 线程池的线程 " + r +" 的执行被拒绝",new RejectedExecutionException());
        // 如果当前线程的优先级高于默认线程优先级，就重开线程去执行它
        if(Thread.currentThread().getPriority() > Thread.NORM_PRIORITY){
            new Thread(r).start();
        }
        // 否则就在当前线程执行它（尽量让其滞后执行）
        //TODO: 这里我有一个问题，调用这个方法的是哪个线程？
        else{
            r.run();
        }
    }
}
