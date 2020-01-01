package com.saltman155.aion.commons.network.dispatcher;

import com.saltman155.aion.commons.network.Acceptor;
import com.saltman155.aion.commons.network.AConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;

/**
 * 调度器：
 *        每个连接调度器实例对应一个线程。连接调度器目前有两个实现：
 *        {@link AcceptDispatcher}          连接请求事件处理调度器
 *        {@link AcceptReadWriteDispatcher} 连接请求、读写请求事件处理调度器
 *        服务端需要将相应的服务端通道事件处理注册到各个调度器上
 *
 * @author: saltman155
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
     * 将一个客户端连接实例注册到这个调度器上
     * @param connection            连接实例，其对应的通道会被注册处到这个调度器对应的选择器上
     * @param ops                   被注册的事件
     * @throws IOException          通道已经关闭
     */
    public final void clientRegister(AConnection connection, int ops) throws IOException{
        synchronized (guard) {
            //解除其他线程在此选择器上的select()方法阻塞
            selector.wakeup();
            //连接实例 同时被设置为 返回键的附件
            connection.setKey(connection.getSocketChannel().register(selector,ops, connection));
        }
    }

    /**
     * 将一个客户端服务通道的事件注册到这个调度器上
     * @param channel               客户端通道
     * @param ops                   事件
     * @param att                   处理接受事件的实例，作为返回键的附件
     * @return                      返回键
     * @throws IOException          通道已经关闭
     */
    public final SelectionKey serverRegister(SelectableChannel channel, int ops, Acceptor att) throws IOException {
        synchronized (guard){
            selector.wakeup();
            return channel.register(selector,ops,att);
        }
    }

    /**
     * 获取这个连接绑定的通道选择器
     * @return      绑定的通道选择器
     */
    public final Selector getSelector() {
        return this.selector;
    }


    /**
     * 调度方法 接受选择器上的事件，或处理连接关闭队列
     * @throws IOException
     */
    protected abstract void dispatch() throws IOException;




}
