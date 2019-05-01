package com.superywd.aion.commons.execute;

import java.util.concurrent.Executor;

/**
 * @author: saltman155
 * @date: 2019/3/22 15:33
 */
public class ThreadPoolManager implements Executor {


    @Override
    public void execute(Runnable command) {

    }




    /**实现单例的内部类*/
    private static class SingletonHolder {
        protected static final ThreadPoolManager instance = new ThreadPoolManager();
    }

    /**获取实例*/
    public static final ThreadPoolManager getInstance() {
        return SingletonHolder.instance;
    }



}
