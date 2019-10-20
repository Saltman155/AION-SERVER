package com.superywd.aion.login.network.aion;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 从登录服务端发往游戏客户端的基数据包
 * @author: saltman155
 * @date: 2019/3/28 19:54
 */
public abstract class ClientPacket implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ServerPacket.class);

    /**最大客户端数据包*/
    public static final int MAX_PACKET_SIZE = 8192 * 2;

    private final int opcode;

    protected final Channel channel;

    protected final ByteBuffer data;

    protected ClientPacket(int opcode, Channel channel,ByteBuffer data) {
        this.opcode = opcode;
        this.channel = channel;
        this.data = data;
    }

    @Override
    public void run() {
        try{
            handler();
        }catch (Exception e){
            logger.error("处理channelId为 {} 的channel，opcode为 {} 的数据包发生异常！",
                    channel.id().asLongText().hashCode(),
                    opcode);
            logger.error(e.getMessage(),e);
        }
    }

    public int getOpcode() {
        return opcode;
    }

    public boolean readable(){
        try{
            readData();
            return true;
        }catch (Exception e){
            logger.error("channelId为 {} 的channel，opcode为 {} 的数据包可读性检查失败！",
                    channel.id().asLongText().hashCode(),
                    opcode);
            logger.error(e.getMessage(),e);
            return false;
        }
    }



    /**
     * 具体数据包执行的逻辑操作
     */
    protected abstract void handler();

    /**
     * 读取数据
     */
    protected abstract void readData();

}
