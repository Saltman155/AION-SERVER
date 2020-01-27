package com.aionstar.login.network.factories;

import com.aionstar.commons.network.BasePacketFactory;
import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.network.mainserver.clientpackets.CM_ACCOUNT_AUTH;
import com.aionstar.login.network.mainserver.clientpackets.CM_GS_CHARACTER;
import com.aionstar.login.network.mainserver.MSChannelAttr;
import com.aionstar.login.network.mainserver.clientpackets.CM_GS_AUTH;
import com.aionstar.login.network.mainserver.clientpackets.CM_MAC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 游戏主服务端处理的封包
 * @author saltman155
 * @date 2020/1/1 17:57
 */

public class MSPacketFactory extends BasePacketFactory {

    private static final Logger logger = LoggerFactory.getLogger(MSPacketFactory.class);

    private MSPacketFactory(){}

    @Override
    public ClientPacket handle(ByteBuf buffer, Channel channel) {
        MSChannelAttr.InnerSessionState state = channel.attr(MSChannelAttr.M_SESSION_STATE).get();
        int opcode = buffer.readByte() & 0xff;
        logger.info("收到主服务端消息！opcode: {}",opcode);
        switch (state) {
            case CONNECTED:{
                switch (opcode){
                    case 0x00: return new CM_GS_AUTH(channel,buffer);
                    case 0x0d: return new CM_MAC(channel,buffer);
                    default:
                        unknownPacket(opcode);
                }
            }
            case AUTHED:{
                switch (opcode){
                    case 0x01: return new CM_ACCOUNT_AUTH(channel,buffer);
                    case 0x08: return new CM_GS_CHARACTER(channel,buffer);
                    default:
                        unknownPacket(opcode);
                }
            }
            default:
                unknownPacket(opcode);
        }
        return null;
    }

    private static class SingletonHolder{
        private static final MSPacketFactory instance = new MSPacketFactory();
    }

    public static MSPacketFactory getInstance(){
        return MSPacketFactory.SingletonHolder.instance;
    }

}
