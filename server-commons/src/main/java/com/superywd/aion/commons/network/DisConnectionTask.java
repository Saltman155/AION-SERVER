package com.superywd.aion.commons.network;

/**
 * 这个类用来承载一个连接被释放时其结束方法的逻辑，
 * 结束方法会交由 DisConnectionThreadPool线程池执行
 * @author: saltman155
 * @date: 2019/3/22 21:58
 */

public class DisConnectionTask implements Runnable {

    /**被关闭的连接的引用*/
    private AConnection connection;


    public DisConnectionTask(AConnection connection){
        this.connection = connection;
    }

    @Override
    public void run() {
        connection.onDisconnect();
    }
}
