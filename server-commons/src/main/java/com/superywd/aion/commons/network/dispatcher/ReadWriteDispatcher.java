package com.superywd.aion.commons.network.dispatcher;

import com.superywd.aion.commons.network.AConnection;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/22 16:21
 */
public class ReadWriteDispatcher extends Dispatcher {


    /**
     * 创建一个调度器，并指定它的名称以及工作线程池
     *
     * @param name     调度器名称
     * @param executor 内部工作线程池
     * @throws IOException 随便抛抛
     */
    public ReadWriteDispatcher(String name, Executor executor) throws IOException {
        super(name, executor);
    }

    @Override
    protected void closeConnection(AConnection connection) {

    }

    @Override
    protected void dispatch() throws IOException {

    }
}
