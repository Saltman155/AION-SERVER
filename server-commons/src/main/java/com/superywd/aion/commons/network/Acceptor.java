package com.superywd.aion.commons.network;

import com.superywd.aion.commons.network.dispatcher.Dispatcher;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 服务端通道请求连接的接收器,用来处理客户端新的连接请求。
 * 这个处理器会处理连接请求，创建出连接对象，初始化它，并将新的连接的读写事件注册到某个调度器上
 *
 * @author: 迷宫的中心
 * @date: 2019/3/22 17:05
 */
public class Acceptor {

    /**
     * 连接工厂
     */
    private final AConnectionFactory factory;

    /**
     * 网络连接控制中心
     */
    private final NioServer nioServer;



    public Acceptor(AConnectionFactory factory, NioServer nioServer) {
        this.factory = factory;
        this.nioServer = nioServer;
    }


    public final void accept(SelectionKey key) throws IOException {
        //先获取到服务端的通道
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        //建立和客户端的连接通道
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        //从NIO网络连接中心中拿一个调度器，用来处理这个新连接的读&写事件
        Dispatcher dispatcher = nioServer.getReadWriteDispatcher();
        //创建连接对象
        AConnection connection = factory.create(socketChannel,dispatcher);
        if(connection == null){
            return;
        }
        //注册读事件
        dispatcher.clientRegister(connection,SelectionKey.OP_READ);
        //最后调用初始化
        connection.initialized();
    }


}
