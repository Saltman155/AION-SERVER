package com.aionstar.commons.network;

import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据包处理
 * @author saltman155
 * @date 2020/1/18 17:47
 */

public abstract class BasePacketFactory {

    private static final Logger logger = LoggerFactory.getLogger(BasePacketFactory.class);

    /**
     * 从获取的字节数据中解析出具体的封包类型
     * @param buffer        获取的字节数据
     * @param channel       连接
     * @return              封包对象
     */
    public abstract ClientPacket handle(ByteBuf buffer, Channel channel);

    /**
     * 获取的数据包无法识别
     * @param opcode    数据包的操作码
     */
    protected void unknownPacket(int opcode){
        logger.warn("收到了一个无法识别的数据包！opcode为 {}",opcode);
    }

}
