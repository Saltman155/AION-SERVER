package com.aionstar.login.network.factories;

import com.aionstar.commons.network.BasePacketFactory;
import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.network.mainserver.MSChannelAttr;
import com.aionstar.login.network.mainserver.clientpackets.CM_GS_AUTH;
import com.aionstar.login.network.mainserver.clientpackets.CM_MAC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 *
 * @author saltman155
 * @date 2020/1/1 17:57
 */

@Component
public class MSPacketFactory extends BasePacketFactory {

    @Override
    public ClientPacket handle(ByteBuf buffer, Channel channel) {
        MSChannelAttr.InnerSessionState state = channel.attr(MSChannelAttr.M_SESSION_STATE).get();
        int opcode = buffer.readByte() & 0xff;
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

            }
            default:
                unknownPacket(opcode);
        }
        return null;
    }

}
