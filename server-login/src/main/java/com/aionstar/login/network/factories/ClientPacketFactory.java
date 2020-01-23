package com.aionstar.login.network.factories;

import com.aionstar.commons.network.BasePacketFactory;
import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.network.client.clientpackets.CM_AUTH_GG;
import com.aionstar.login.network.client.clientpackets.CM_LOGIN;
import com.aionstar.login.network.client.clientpackets.CM_PLAY;
import com.aionstar.login.network.client.clientpackets.CM_SERVER_LIST;
import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 客户端发送的封包解析工厂，从字节数据中，解析出数据包对象
 */

public class ClientPacketFactory extends BasePacketFactory {

    private static final Logger logger = LoggerFactory.getLogger(ClientPacketFactory.class);

    private ClientPacketFactory(){}

    @Override
    public ClientPacket handle(ByteBuf buffer, Channel channel){
        ClientChannelAttr.SessionState state = channel.attr(ClientChannelAttr.C_SESSION_STATE).get();
        //紧跟代表数据包长度的后一个字节，其意义是这个数据包的类型
        int opcode = buffer.readByte() & 0xff;
        logger.info("收到客户端消息！opcode: {}",opcode);
        switch (state){
            case CONNECTED:{
                switch (opcode){
                    case 0x07: return new CM_AUTH_GG(channel,buffer);
                    case 0x08: break;
                    default: unknownPacket(opcode);
                }
                break;
            }
            case AUTHED_GG:{
                switch (opcode){
                    case 0x0B: return new CM_LOGIN(channel,buffer);
                    default: unknownPacket(opcode);
                }
                break;
            }
            case AUTHED_LOGIN:{
                switch (opcode){
                    case 0x05: return new CM_SERVER_LIST(channel,buffer);
                    case 0x02: return new CM_PLAY(channel,buffer);
                    default:
                        unknownPacket(opcode);
                }
                break;
            }
        }
        return null;
    }

    private static class SingletonHolder{
        private static final ClientPacketFactory instance = new ClientPacketFactory();
    }

    public static ClientPacketFactory getInstance(){
        return SingletonHolder.instance;
    }


}
