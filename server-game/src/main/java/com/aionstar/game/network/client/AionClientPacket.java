package com.aionstar.game.network.client;

import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * 客户端发送给主服务端的封包
 * 与 ${@link ClientPacket} 没有区别，唯一的区别就是 对部分方法进行了重写
 * @author saltman155
 * @date 2020/1/24 0:55
 */

public abstract class AionClientPacket extends ClientPacket {


    protected AionClientPacket(byte opcode, Channel channel, ByteBuf data) {
        super(opcode, channel, data);
    }

    /**
     * 数据包结构复制
     * @return          新的封包结构对象
     */
    public AionClientPacket clonePacket(){
        try {
            return (AionClientPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
