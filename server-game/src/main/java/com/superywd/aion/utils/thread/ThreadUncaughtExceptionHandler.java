package com.superywd.aion.utils.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.UncaughtExceptionHandler;

/**
 * 该类用于处理线程中没有被捕获的异常
 * @author: saltman155
 * @date: 2019/5/4 23:12
 */
public class ThreadUncaughtExceptionHandler implements UncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ThreadUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("在线程 {} 中发生了未捕获的异常 {}",t.getName(),e.toString());
        logger.error(e.getMessage(),e);
        if(e instanceof OutOfMemoryError){
            logger.error("堆内存溢出！请检查代码是否有问题或 -Xmx 扩大运行内存！");
        }
    }
}
