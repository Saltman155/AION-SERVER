package com.aionstar.game.model.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 登录服务器网络配置
 * @author saltman155
 * @date 2020/1/5 1:34
 */

@Component
@ConfigurationProperties(prefix = "main-server.network.login-server")
public class LoginNetwork {

    private int serverId;
    private String serverPassword;
    private int thread;
    private int bufferSize;
    private String remoteAddr;
    private int remotePort;

    public int getServerId() { return serverId; }
    public void setServerId(int serverId) { this.serverId = serverId; }
    public String getServerPassword() { return serverPassword; }
    public void setServerPassword(String serverPassword) { this.serverPassword = serverPassword; }
    public int getThread() { return thread; }
    public void setThread(int thread) { this.thread = thread; }
    public int getBufferSize() { return bufferSize; }
    public void setBufferSize(int bufferSize) { this.bufferSize = bufferSize; }
    public String getRemoteAddr() { return remoteAddr; }
    public void setRemoteAddr(String remoteAddr) { this.remoteAddr = remoteAddr; }
    public int getRemotePort() { return remotePort; }
    public void setRemotePort(int remotePort) { this.remotePort = remotePort; }

    private static LoginNetwork instance;

    @PostConstruct
    private void buildInstance(){
        LoginNetwork.instance = this;
    }

    public static LoginNetwork getInstance(){
        return instance;
    }
}
