package com.superywd.aion.commons.network;

/**
 * 服务端网络配置包装类
 * @author: saltman155
 * @date: 2019/3/21 19:16
 */
public class ServerConfig {

    /**用于创建连接服务的主机地址*/
    public final String host;

    /**用于创建连接服务的主机端口*/
    public final int port;

    /**连接服务名称*/
    public final String connectionName;

    /**创建新的连接类 {@link com.superywd.aion.commons.network.AConnection}的工厂*/
    public final AConnectionFactory factory;


    public ServerConfig(String host, int port, String connectionName, AConnectionFactory factory) {
        this.host = host;
        this.port = port;
        this.connectionName = connectionName;
        this.factory = factory;
    }
}
