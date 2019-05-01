package com.superywd.aion.commons.network;

import com.superywd.aion.commons.execute.ThreadPoolManager;
import com.superywd.aion.commons.network.dispatcher.AcceptDispatcher;
import com.superywd.aion.commons.network.dispatcher.AcceptReadWriteDispatcher;
import com.superywd.aion.commons.network.dispatcher.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NIO网络连接中心
 * @author: saltman155
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

    public NioServer(int readWriteThreadCount,ServerConfig... serverConfigs){

        //TODO: 原版服务端在这块还有断言的设置，我就不做了
        //一通设置
        this.workPool = ThreadPoolManager.getInstance();
        this.readWriteDispatcherBalanceCount = new AtomicInteger(0);
        this.readWriteThreadCount = new AtomicInteger(readWriteThreadCount);
        this.serverConfigList = serverConfigs;
    }

    /**
     * 创建-初始化所有的调度器
     * @param readWriteThreadCount      读写事件处理调度器（线程）数量
     * @param workPool                  用来干活的线程池
     * @throws IOException
     */
    private void initDispatchers(int readWriteThreadCount,Executor workPool) throws IOException {
        //如果readWriteThreadCount被设置为0，则只启动一个调度器
        if(readWriteThreadCount < 1){
            acceptDispatcher = new AcceptReadWriteDispatcher("连接及读写请求处理调度器", workPool);
            acceptDispatcher.start();
        }else{
            //连接请求调度器创建启动
            acceptDispatcher = new AcceptDispatcher("连接请求处理调度器");
            acceptDispatcher.start();
            //读写请求调度器创建启动
            readWriteDispatchers = new AcceptReadWriteDispatcher[readWriteThreadCount];
            for (int i = 0; i < readWriteDispatchers.length; i++) {
                readWriteDispatchers[i] = new AcceptReadWriteDispatcher("【" + i + "】号读写请求处理调度器", workPool);
                readWriteDispatchers[i].start();
            }

        }

    }


    public void OpenConnection() throws IOException {
        //初始化&启动所有的调度器
        initDispatchers(readWriteThreadCount.get(),workPool);
        //然后注册打开所有的服务通道
        for(ServerConfig config : serverConfigList){
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            //非阻塞模式
            serverChannel.configureBlocking(false);
            InetSocketAddress address;
            if("*".equals(config.host)){
                address = new InetSocketAddress(config.port);
                logger.info("连接通道 {} 将在端口 {} 上监听来自任意ip的请求...",config.connectionName,config.port);
            }else{
                address = new InetSocketAddress(config.host,config.port);
                logger.info("连接通道 {} 将在端口 {} 上监听来自特定ip {} 的请求...",config.connectionName,config.port,config.host);
            }
            //一波监听
            serverChannel.socket().bind(address);
            //请求连接事件注册
            SelectionKey acceptKey = getAcceptDispatcher()
                    .serverRegister(serverChannel,SelectionKey.OP_ACCEPT,new Acceptor(config.factory,this));
            serverChannelKeys.add(acceptKey);
        }

    }

    /**
     * 获取一个请求连接处理调度器
     * @return      请求连接处理调度器
     */
    public Dispatcher getAcceptDispatcher() {
        return acceptDispatcher;
    }

    /**
     * 获取一个当前负载较低的读写处理调度器
     * @return      读写处理调度器
     */
    public Dispatcher getReadWriteDispatcher() {
        //如果readWriteDispatchers是空的，说明是用的请求&读写共用调度器，直接返回
        if(readWriteDispatchers == null || readWriteDispatchers.length == 0){
            return acceptDispatcher;
        }
        //否则是存在读写处理调度器的，按照依次分配的负载均衡思想排一个出来处理
        if(readWriteDispatchers.length == 1){
            return readWriteDispatchers[0];
        }
        //TODO： 这里个人感觉用 AtomicInteger不靠谱，还是得上重量级锁
        if(readWriteDispatcherBalanceCount.get() > readWriteDispatchers.length){
            readWriteDispatcherBalanceCount.set(0);
        }
        return readWriteDispatchers[readWriteDispatcherBalanceCount.get()];
    }


}
