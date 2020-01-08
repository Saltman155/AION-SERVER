package com.saltman155.aion.game.model.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
    private int writeBufferSize;
    private int readBufferSize;

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    public int getBossTread() { return bossTread; }
    public void setBossTread(int bossTread) { this.bossTread = bossTread; }
    public int getWorkerThread() { return workerThread; }
    public void setWorkerThread(int workerThread) { this.workerThread = workerThread; }
    public int getWriteBufferSize() { return writeBufferSize; }
    public void setWriteBufferSize(int writeBufferSize) { this.writeBufferSize = writeBufferSize; }
    public int getReadBufferSize() { return readBufferSize; }
    public void setReadBufferSize(int readBufferSize) { this.readBufferSize = readBufferSize; }

}
