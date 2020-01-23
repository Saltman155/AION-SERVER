package com.aionstar.game.network.loginserver;

import com.aionstar.commons.network.BaseChannelAttr;
import com.aionstar.commons.network.BasePacketFactory;
import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.game.network.loginserver.clientpackets.CM_BAN_MAC_LIST;
import com.aionstar.game.network.loginserver.clientpackets.CM_GS_AUTH_RPS;
import com.aionstar.game.network.loginserver.clientpackets.CM_GS_CHARACTER;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录服务器发送的封包解析
 * @author saltman155
 * @date 2020/1/18 18:10
 */

public class LSPacketFactory extends BasePacketFactory {

    private static final Logger logger = LoggerFactory.getLogger(LSPacketFactory.class);

    /**用于存放封包类型的映射表*/
    private static final Map<LSChannelAttr.InnerSessionState,Map<Integer,ClientPacket>> PACKET_MAP = new HashMap<>();

    private LSPacketFactory(){}

    @Override
    public ClientPacket handle(ByteBuf buffer, Channel channel) {
        //获取连接状态
        LSChannelAttr.InnerSessionState state = channel.attr(LSChannelAttr.LS_SESSION_STATE).get();
        int code = buffer.readByte() & 0xff;
        return getPacket(state,code,buffer,channel);
    }

    /**
     * 从封包映射表里根据参数，获取一个封包结构的复制
     * @param state         状态参数
     * @param code          操作码参数
     * @param buf           需要传递到封包里的buf
     * @param channel       需要传递到封包里的channel
     * @return              完整的封包
     */
    private ClientPacket getPacket(LSChannelAttr.InnerSessionState state,
                                   int code, ByteBuf buf, Channel channel) {
        ClientPacket packet = null;
        Map<Integer, ClientPacket> pm = PACKET_MAP.get(state);
        if (pm != null) {
            packet = pm.get(code);
        }
        if(packet == null){
            unknownPacket(code);
            return null;
        }
        //注意，这里是克隆一个包对象
        ClientPacket result = packet.clonePacket();
        result.setData(buf);
        result.setChannel(channel);
        return result;
    }

    /**
     * 载入所有登录服务器发送的封包结构
     */
    public static void loadAllPacket(){
        addPacketPrototype(new CM_GS_AUTH_RPS((byte) 0x00,null,null),LSChannelAttr.InnerSessionState.CONNECTED);
        addPacketPrototype(new CM_GS_CHARACTER((byte) 0x08,null,null),LSChannelAttr.InnerSessionState.AUTHED);
        addPacketPrototype(new CM_BAN_MAC_LIST((byte) 0x09,null,null),LSChannelAttr.InnerSessionState.AUTHED);
    }

    /**
     * 添加一些封包结构
     * @param packet        封包结构
     * @param states        有效的连接状态
     */
    private static void addPacketPrototype(ClientPacket packet,LSChannelAttr.InnerSessionState... states){
        for (LSChannelAttr.InnerSessionState state : states) {
            Map<Integer, ClientPacket> pm = PACKET_MAP.computeIfAbsent(state, k -> new HashMap<>());
            pm.put(packet.getOpcode(), packet);
        }
    }

    static {
        //载入封包
        loadAllPacket();
    }

    private static class SingletonHolder{
        private static final LSPacketFactory instance = new LSPacketFactory();
    }

    public static LSPacketFactory getInstance(){
        return SingletonHolder.instance;
    }

}
