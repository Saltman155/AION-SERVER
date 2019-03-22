package com.superywd.aion.commons.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NIO网络连接中心
 * @author: 迷宫的中心
 * @date: 2019/3/22 14:42
 */
public class NioServer {

    private static final Logger logger = LoggerFactory.getLogger(NioServer.class);

    /**服务器对外所有通道服务被注册后返回的SelectionKey容器*/
    private final List<SelectionKey> serverChannelKeys = new ArrayList<SelectionKey>();

    /**待注册的通道服务*/
    private ServerConfig[] serverConfigList;

    /**通道请求连接控制调度器*/
    private Dispatcher acceptDispatcher;

    /**通道读写控制调度器集*/
    private Dispatcher[] readWriteDispatchers;

    /**用于通道读写控制调度器负载均衡用的计数器*/
    private AtomicInteger readWriteDispatcherBalanceCount;

    /**干活的线程池*/
    private final Executor workPool;

    /**处理读写事件的线程数量*/
    private AtomicInteger readWriteThreadCount;

    public NioServer(int readWriteThreads,ServerConfig... serverConfigs){

    }


}
