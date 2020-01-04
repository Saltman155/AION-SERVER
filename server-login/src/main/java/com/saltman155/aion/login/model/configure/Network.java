package com.saltman155.aion.login.model.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author saltman155
 * @date 2020/1/1 16:53
 */

@Component
@ConfigurationProperties(prefix = "login-server.network")
public class Network {

    public Client client;

    public MainServer mainServer;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public MainServer getMainServer() {
        return mainServer;
    }

    public void setMainServer(MainServer mainServer) {
        this.mainServer = mainServer;
    }

    public static class Client{
        private int port;
        private int bossThread;
        private int workerThread;
        private int writeBufferSize;
        private int readBufferSize;

        public int getPort() { return port; }
        public void setPort(int port) { this.port = port; }
        public int getBossThread() { return bossThread; }
        public void setBossThread(int bossThread) { this.bossThread = bossThread; }
        public int getWorkerThread() { return workerThread; }
        public void setWorkerThread(int workerThread) { this.workerThread = workerThread; }
        public int getWriteBufferSize() { return writeBufferSize; }
        public void setWriteBufferSize(int writeBufferSize) { this.writeBufferSize = writeBufferSize; }
        public int getReadBufferSize() { return readBufferSize; }
        public void setReadBufferSize(int readBufferSize) { this.readBufferSize = readBufferSize; }
    }

    public static class MainServer{
        private int port;
        private int thread;
        private boolean pingpongCheck;
        private int writeBufferSize;
        private int readBufferSize;

        public int getPort() { return port; }
        public void setPort(int port) { this.port = port; }
        public int getThread() { return thread; }
        public void setThread(int thread) { this.thread = thread; }
        public boolean isPingpongCheck() { return pingpongCheck; }
        public void setPingpongCheck(boolean pingpongCheck) { this.pingpongCheck = pingpongCheck; }
        public int getWriteBufferSize() { return writeBufferSize; }
        public void setWriteBufferSize(int writeBufferSize) { this.writeBufferSize = writeBufferSize; }
        public int getReadBufferSize() { return readBufferSize; }
        public void setReadBufferSize(int readBufferSize) { this.readBufferSize = readBufferSize; }
    }


}
