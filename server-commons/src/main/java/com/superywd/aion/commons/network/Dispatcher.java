package com.superywd.aion.commons.network;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;

/**
 * 连接调度器
 *
 * @author: 迷宫的中心
 * @date: 2019/3/21 19:36
 */
public abstract class Dispatcher extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    /**通道选择器*/
    protected Selector selector;

    /**执行特定操作的线程池*/
    protected final Executor workPool;

    /**用于保证线程同步的锁*/
    protected final Object guard = new Object();


    /**
     * 创建一个调度器，并指定它的名称以及工作线程池
     * @param name          调度器名称
     * @param executor      内部工作线程池
     * @throws IOException  随便抛抛
     */
    public Dispatcher(String name,Executor executor) throws IOException {
        super(name);
        this.selector = SelectorProvider.provider().openSelector();
        this.workPool = executor;
    }

    /**
     * 获取这个连接绑定的通道选择器
     * @return      绑定的通道选择器
     */
    public final Selector getSelector() {
        return this.selector;
    }

    /**
     * 将一个连接放入关闭队列中，它稍后将被调度方法（dispatch）关闭
     * @param connection    待关闭的连接
     */
    protected abstract void closeConnection(AConnection connection);

    /**
     * 调度方法 接受选择器上的事件，或处理连接关闭队列
     * @throws IOException
     */
    protected abstract void dispatch() throws IOException;

    @Override
    public void run(){
        while(true){
            try{
                dispatch();
                //TODO: 这里我不是很理解这个同步锁有什么用
                synchronized (guard){}
            }catch (Exception e){
                logger.error("调度发生异常!"+e.getMessage(),e);
            }
        }
    }


    /**
     * 将一个连接实例注册到这个调度器上
     * @param connection            连接实例，其对应的通道会被注册处到这个调度器对应的选择器上
     * @param ops                   被注册的事件
     * @return                      注册键
     * @throws IOException          通道已经关闭
     */
    public final SelectionKey register(AConnection connection, int ops) throws IOException{
        synchronized (guard) {
            //解除其他线程在此选择器上的select阻塞
            selector.wakeup();
            //连接实例同时被设置为返回键的附件
            return connection.getChannel().register(selector,ops,connection);
        }
    }

}
