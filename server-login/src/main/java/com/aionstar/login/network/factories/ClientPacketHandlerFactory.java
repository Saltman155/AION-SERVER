package com.aionstar.login.network.factories;

import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.client.clientpackets.CM_AUTH_GG;
import com.aionstar.login.network.client.clientpackets.CM_LOGIN;
import com.aionstar.login.network.client.clientpackets.CM_SERVER_LIST;
import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 数据包解析工厂，从二进制数据，生成出客户端具体的数据包对象
 */
public class ClientPacketHandlerFactory {

    private static final Logger logger = LoggerFactory.getLogger(ClientPacketHandlerFactory.class);

    public static ClientPacket handle(ByteBuf buffer, Channel channel){
        ClientChannelAttr.SessionState state = channel.attr(ClientChannelAttr.C_SESSION_STATE).get();
        //紧跟代表数据包长度的后一个字节，其意义是这个数据包的类型
        int type = buffer.readByte() & 0xff;
        logger.info("解析出type为: {}",type);
        switch (state){
            case CONNECTED:{
                switch (type){
                    case 0x07: return new CM_AUTH_GG(channel,buffer);
                    case 0x08: break;
                    default: unknownPacket(state,type);
                }
                break;
            }
            case AUTHED_GG:{
                switch (type){
                    case 0x0B: return new CM_LOGIN(channel,buffer);
                    default: unknownPacket(state,type);
                }
                break;
            }
            case AUTHED_LOGIN:{
                switch (type){
                    case 0x05: return new CM_SERVER_LIST(channel,buffer);
                    case 0x02: break;
                    default:
                        unknownPacket(state, type);
                }
                break;
            }
        }
        return null;
    }

    private static void unknownPacket(ClientChannelAttr.SessionState state, int id){
        logger.warn("状态为{}的连接,收到了一个无法识别的数据包！类型id为 {}",state,id);
    }

}
