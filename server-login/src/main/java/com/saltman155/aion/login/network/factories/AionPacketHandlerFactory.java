package com.saltman155.aion.login.network.factories;

import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.login.network.client.ClientPacket;
import com.saltman155.aion.login.network.client.clientpackets.response.CM_AUTH_GG_RESPONSE;
import com.saltman155.aion.login.network.client.clientpackets.request.CM_LOGIN_REQUEST;
import com.saltman155.aion.login.network.client.clientpackets.request.CM_SERVER_LIST_REQUEST;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 数据包解析工厂，从二进制数据，生成出客户端具体的数据包对象
 */
public class AionPacketHandlerFactory {

    private static final Logger logger = LoggerFactory.getLogger(AionPacketHandlerFactory.class);

    public static ClientPacket handle(ByteBuffer buffer, Channel channel){
        ClientChannelAttr.SessionState state = channel.attr(ClientChannelAttr.SESSION_STATE).get();
        //紧跟代表数据包长度的后一个字节，其意义是这个数据包的类型
        int type = buffer.get() & 0xff;
        logger.info("解析出type为: {}",type);
        switch (state){
            case CONNECTED:{
                switch (type){
                    case 0x07: return new CM_AUTH_GG_RESPONSE(channel,buffer);
                    case 0x08: break;
                    default: unknownPacket(state,type);
                }
                break;
            }
            case AUTHED_GG:{
                switch (type){
                    case 0x0B: return new CM_LOGIN_REQUEST(channel,buffer);
                    default: unknownPacket(state,type);
                }
                break;
            }
            case AUTHED_LOGIN:{
                switch (type){
                    case 0x05: return new CM_SERVER_LIST_REQUEST(channel,buffer);
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
