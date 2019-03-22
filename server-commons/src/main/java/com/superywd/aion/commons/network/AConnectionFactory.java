package com.superywd.aion.commons.network;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * 这个接口定义了创建连接的工厂类方法
 * @author: 迷宫的中心
 * @date: 2019/3/21 19:26
 */
public interface AConnectionFactory {


    /**
     * 创建一个新的连接
     *
     * @param socket            连接对象
     * @param dispatcher        调度器
     * @return                  连接实例
     * @throws IOException
     */
    AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException;

}
