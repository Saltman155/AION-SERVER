package com.aionstar.commons.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 接收的数据包基类
 * @author: saltman155
 * @date: 2019/3/28 19:54
 */
public abstract class ClientPacket implements Runnable,Cloneable {

    private static final Logger logger = LoggerFactory.getLogger(ClientPacket.class);

    private final byte opcode;

    protected Channel channel;

    protected ByteBuf data;

    protected ClientPacket(byte opcode, Channel channel, ByteBuf data) {
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
                    channel.id().asLongText().hashCode(), opcode);
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
            logger.error("channelId为 {} 的channel，opcode为 {} 的数据包数据读取失败！",
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

    /**
     * 设置channel
     * @param channel   待设置的channel
     */
    public void setChannel(Channel channel) { this.channel = channel; }

    /**
     * 设置data数据
     * @param data      待设置的数据
     */
    public void setData(ByteBuf data) { this.data = data; }

    /**
     * 数据包结构复制
     * @return          新的封包结构对象
     */
    public ClientPacket clonePacket(){
        try {
            return (ClientPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
