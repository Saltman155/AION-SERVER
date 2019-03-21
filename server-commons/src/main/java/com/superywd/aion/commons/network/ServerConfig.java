package com.superywd.aion.commons.network;

/**
 * 服务端网络配置包装类
 * @author: 迷宫的中心
 * @date: 2019/3/21 19:16
 */
public class ServerConfig {

    /**用于创建连接服务的主机地址*/
    public final String host;

    /**用于创建连接服务的主机端口*/
    public final int port;

    /**连接服务名称*/
    public final String connectionName;

    /**
     * 创建新的连接类 ${@link com.superywd..}的工厂
     *
     */
    public final ConnectionFactory factory;
}
