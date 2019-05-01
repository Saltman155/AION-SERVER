package com.superywd.aion.commons.network;

import com.superywd.aion.commons.network.dispatcher.AcceptReadWriteDispatcher;
import com.superywd.aion.commons.network.dispatcher.Dispatcher;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 表示与服务器建立的连接的类
 * 连接由 {@link com.superywd.aion.commons.network.AConnectionFactory} 创建
 * 并绑定到一个特定的 {@link java.nio.channels.SelectionKey } 选择器上
 * 然后注册到特定的 {@link Dispatcher} 调度器上
 * @author: saltman155
 * @date: 2019/3/21 19:22
 */
public abstract class AConnection {

    /**与客户端通讯的通道*/
    private final SocketChannel socketChannel;

    /**注册的调度器*/
    private final Dispatcher dispatcher;

    /**注册的选择器*/
    private SelectionKey key;

    /**如果在发送最后一个数据包后就需要关闭这个连接的，设置为true*/
    protected boolean pendingClose;

    /**该连接是否是强制关闭的（即被调用 onDisconnect()方法后立即关闭）*/
    protected boolean isForcedClosing;

    /**是否是已经关闭的连接*/
    protected boolean closed;

    /**作为同步的监视器（锁）*/
    protected final Object guard = new Object();

    /**保存写入操作的buffer*/
    public final ByteBuffer writeBuffer;

    /**读取操作的buffer*/
    public final ByteBuffer readBuffer;

    /**缓存的连接远端ip（为了在连接关闭后还能够拿到ip）*/
    private final String ip;

    /**PacketProcessed 过程是否保存同步*/
    private boolean locked = false;


    public AConnection(SocketChannel socketChannel, Dispatcher dispatcher,int wbSize,int rbSize) {
        this.socketChannel = socketChannel;
        this.dispatcher = dispatcher;

        writeBuffer = ByteBuffer.allocate(wbSize);
        writeBuffer.flip();
        //小端编码
        writeBuffer.order(ByteOrder.LITTLE_ENDIAN);
        readBuffer = ByteBuffer.allocate(rbSize);
        readBuffer.order(ByteOrder.LITTLE_ENDIAN);
        //获取ip
        this.ip = socketChannel.socket().getInetAddress().getHostAddress();
    }

    /**
     * 设置选择器 在将连接对象注册到某个调度器后调用这个方法
     * @param key   被设置的选择器
     */
    public final void setKey(SelectionKey key){
        this.key = key;
    }

    /**获取对应的返回键*/
    public final SocketChannel getSocketChannel() {
        return this.socketChannel;
    }

    /**获取对应的调度器*/
    public final Dispatcher getDispatcher() { return this.dispatcher; }

    /**获取连接的远端ip*/
    public final String getIp() { return ip; }

    /**
     * @return 连接是否已经不能写入
     */
    public final boolean isWriteDisabled() {
        return pendingClose || closed;
    }

    /**
     * @return 这个连接是否处于即将被关闭（但未关闭）的状态
     */
    public final boolean isPendingClose() {
        return pendingClose && !closed;
    }

    /**
     * 通知注册的调度器（selector）处理写入的数据
     */
    protected final void enableWriteInterest() {
        //如果对应的key是有效的，则设置key为存在写数据的状态，并通知对应的selector（取消其在select()方法上的阻塞）
        if (key.isValid()) {
            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
            key.selector().wakeup();
        }
    }



    /**
     * 关闭一个连接
     * 这个连接会被放到其对应的调度器的关闭队列中，它将会在调度器处理下一个事件前得到处理
     * @param forced
     */
    public final void close(boolean forced) {
        synchronized (guard) {
            if (isWriteDisabled()) {
                return;
            }
            isForcedClosing = forced;
            ((AcceptReadWriteDispatcher)getDispatcher()).pendingCloseConnection(this);
        }
    }


    /**
     * 这个方法将直接关闭这个连接，而不会做其他的操作（如调用onDisconnect()方法）
     * 如果这个连接实际上已经被关闭了，则返回false，如果当前处于可关闭的状态，则返回true
     * 不管返回结果如何，连接最后一定是关闭的状态
     * @return      在调用前是否是存活的
     */
    public final boolean onlyClose(){
        //判断一下是不是这个连接注册的调度器对应的线程来执行的这个关闭操作
        assert Thread.currentThread() == dispatcher;
        synchronized (guard){
            if(closed){
                return false;
            }
            try{
                //如果还是存活的，就关闭它
                if(socketChannel.isOpen()){
                    socketChannel.close();
                    //将对应返回键的绑定与返回键自身释放
                    key.attach(null);
                    key.cancel();
                }
                closed = true;
            }catch (IOException e){
                //忽略错误
            }
        }
        return true;
    }

    /**
     * 解析数据的方法，由调度器调用
     * @return      数据是否解析成功。当解析失败时，调度器将终止这个连接
     */
    public abstract boolean processData(ByteBuffer data);

    /**
     * 这个方法作为向远端写入数据包的主要方法，具体的各个连接子类（如面向游戏客户端的连接类，或面向游戏主逻辑服务端的连接类）应实现此方法。
     * 当返回true时，会被调度器持续调用，直到这个方法返回了false（即表示没有更多数据可写了）
     * @param data  需要写入的目标buffer
     * @return      是否还有数据要写入
     */
    public abstract boolean writePackData(ByteBuffer data);

    /**
     * 在AConnection对象完全初始化并准备好处理与发送数据包时调用。
     * 它可以作为一个钩子函数，如发送第一个数据包等。
     */
    abstract protected void initialized();

    /**
     * 调度器会在这个连接第一次关闭时调用这个方法，这个方法只会调用一次。
     * 适合用来做一些清除操作
     */
    abstract protected void onDisconnect();

    /**
     * 这个方法会在网络服务器关闭的时候被调用，这个方法只会被调用一次
     */
    abstract protected void onServerClose();
}
