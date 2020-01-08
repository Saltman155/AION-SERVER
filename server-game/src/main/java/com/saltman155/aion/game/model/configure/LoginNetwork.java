package com.saltman155.aion.game.model.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 登录服务器网络配置
 * @author saltman155
 * @date 2020/1/5 1:34
 */

@Component
@ConfigurationProperties(prefix = "main-server.network.login-server")
public class LoginNetwork {

    private int thread;
    private int writeBufferSize;
    private int readBufferSize;
    private String remoteAddr;
    private int remotePort;

    public int getThread() { return thread; }
    public void setThread(int thread) { this.thread = thread; }
    public int getWriteBufferSize() { return writeBufferSize; }
    public void setWriteBufferSize(int writeBufferSize) { this.writeBufferSize = writeBufferSize; }
    public int getReadBufferSize() { return readBufferSize; }
    public void setReadBufferSize(int readBufferSize) { this.readBufferSize = readBufferSize; }
    public String getRemoteAddr() { return remoteAddr; }
    public void setRemoteAddr(String remoteAddr) { this.remoteAddr = remoteAddr; }
    public int getRemotePort() { return remotePort; }
    public void setRemotePort(int remotePort) { this.remotePort = remotePort; }
}
