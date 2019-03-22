package com.superywd.aion.commons.network;

import com.superywd.aion.commons.network.dispatcher.Dispatcher;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 表示与服务器建立的连接的类
 * 连接由 {@link com.superywd.aion.commons.network.AConnectionFactory} 创建
 * 并绑定到一个特定的 {@link java.nio.channels.SelectionKey } 选择器上
 * 然后注册到特定的 {@link Dispatcher} 调度器上
 * @author: 迷宫的中心
 * @date: 2019/3/21 19:22
 */
public class AConnection {

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
     * @param key
     */
    final void setKey(SelectionKey key){
        this.key = key;
    }

    final SocketChannel getChannel(){
        return this.socketChannel;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
