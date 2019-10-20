package com.superywd.aion.login.network.aion;

import com.superywd.aion.commons.network.AConnection;
import com.superywd.aion.commons.network.dispatcher.Dispatcher;
import com.superywd.aion.login.network.crypt.EncryptedRSAKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 与aion客户端连接的数据包对象,继承自类 {@link com.superywd.aion.commons.network.AConnection}
 * @author: saltman155
 * @date: 2019/3/22 23:04
 */

public class LoginConnection extends AConnection {

    private static final Logger logger = LoggerFactory.getLogger(LoginConnection.class);

    private static final int READ_BUFFER_SIZE = 8192*2;

    private static final int WRITE_BUFFER_SIZE = 8192*2;


    /**连接的会话id（直接由hashCode方法生成）*/
    public int sessionId = hashCode();
    /**与客户端的RSA加密秘钥对*/
    private EncryptedRSAKeyPair encryptedRSAKeyPair;
    /**此连接当前的状态*/
    private State state;
    /**连接状态枚举类*/
    public static enum State{
        //表示与客户端仅仅是连接
        CONNECTED,
        //表示已经通过了客户端的GameGuard验证
        AUTHENTIC,
        //表示已经登录
        LOGINED
    }

    public LoginConnection(SocketChannel socketChannel, Dispatcher dispatcher) {
        super(socketChannel, dispatcher, READ_BUFFER_SIZE, WRITE_BUFFER_SIZE);
    }


    /**获取连接会话id*/
    public int getSessionId() {
        return sessionId;
    }
    /**获取这个连接的状态*/
    public State getState(){ return this.state; }
    /**获取已经加密的数据*/
    public byte[] getEncryptedModulus(){
        return encryptedRSAKeyPair.getEncryptedModulus();
    }
    /**对一段数据包进行加密*/
    public final int encrypt(ByteBuffer buffer){
        int size = 0;
        return size;
    }
    /**解析数据*/
    @Override
    public boolean processData(ByteBuffer data) {
//        if(!decrypt(data)){
//            return false;
//        }

        return false;
    }

    @Override
    public boolean writePackData(ByteBuffer data) {
        return false;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void onDisconnect() {
        state = State.CONNECTED;
        logger.info("一个IP来自 {} 的登录尝试...",getIp());
        //TODO: 下面即将进入与客户端的交互
//        encryptedRSAKeyPair = LKeyGenerator.getEncryptedRSAKeyPair();
//        SecretKey blowfishKey = LKeyGenerator.generateBlowfishKey();


    }

    @Override
    protected void onServerClose() {

    }
}
