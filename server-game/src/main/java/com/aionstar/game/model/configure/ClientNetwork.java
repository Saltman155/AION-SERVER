package com.aionstar.game.model.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 连接客户端的网络配置
 * @author saltman155
 * @date 2020/1/5 1:57
 */
@Component
@ConfigurationProperties(prefix = "main-server.network.client")
public class ClientNetwork {

    private int port;
    private int bossTread;
    private int workerThread;
    private int bufferSize;

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    public int getBossTread() { return bossTread; }
    public void setBossTread(int bossTread) { this.bossTread = bossTread; }
    public int getWorkerThread() { return workerThread; }
    public void setWorkerThread(int workerThread) { this.workerThread = workerThread; }
    public int getBufferSize() { return bufferSize; }
    public void setBufferSize(int bufferSize) { this.bufferSize = bufferSize; }

    private static ClientNetwork instance;

    @PostConstruct
    private void buildInstance(){
        ClientNetwork.instance = this;
    }

    public static ClientNetwork getInstance() {
        return instance;
    }
}
