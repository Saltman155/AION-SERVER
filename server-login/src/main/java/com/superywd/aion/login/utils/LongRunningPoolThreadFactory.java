package com.superywd.aion.login.utils;

import java.util.concurrent.ThreadFactory;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/18 23:53
 */

public class LongRunningPoolThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
