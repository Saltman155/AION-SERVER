package com.aionstar.game.network.client;

import com.aionstar.commons.network.BasePacketFactory;
import com.aionstar.game.network.client.clientpackets.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 专门用来处理客户端封包结构的封包解析器
 */
public class ClientPacketFactory extends BasePacketFactory {

    private static final Logger logger = LoggerFactory.getLogger(ClientPacketFactory.class);

    /**用于存放封包类型的映射表*/
    private static final Map<ClientChannelAttr.SessionState, Map<Integer,AionClientPacket>> PACKET_MAP = new HashMap<>();

    private ClientPacketFactory(){}

    @Override
    public AionClientPacket handle(ByteBuf buffer, Channel channel) {
        ClientChannelAttr.SessionState state = channel.attr(ClientChannelAttr.SESSION_STATE).get();
        int code = buffer.readByte() & 0xFF;
        //再固定读两个字节（固定的一个值和id的取反）
        buffer.readShortLE();
        //获取数据
        return getPacket(state,code,buffer,channel);
    }


    private AionClientPacket getPacket(ClientChannelAttr.SessionState state,
                                   int code, ByteBuf buf, Channel channel) {
        AionClientPacket prototype = null;
        Map<Integer, AionClientPacket> pm = PACKET_MAP.get(state);
        if (pm != null) {
            prototype = pm.get(code);
        }
        if(prototype == null){
            unknownPacket(code);
            return null;
        }
        //注意，这里是克隆一个包对象
        AionClientPacket result = prototype.clonePacket();
        result.setData(buf);
        result.setChannel(channel);
        return result;
    }

    /**
     * 添加一些封包结构
     * @param packet        封包结构
     * @param states        有效的连接状态
     */
    private static void addPacketPrototype(AionClientPacket packet, Set<ClientChannelAttr.SessionState> states){
        for (ClientChannelAttr.SessionState state : states) {
            Map<Integer, AionClientPacket> pm = PACKET_MAP.computeIfAbsent(state, k -> new HashMap<>());
            pm.put(packet.getOpcode(), packet);
        }
    }

    /**
     * 所有的客户端封包结构
     */
    private static void loadAllPacket() {
        AionClientPacket packet;
        //各种客户端封包结构
        addPacketPrototype((packet = new CM_VERSION_CHECK((byte)0xF3)),packet.getValidState());
        addPacketPrototype((packet = new CM_TIME_CHECK((byte) 0xFD)),packet.getValidState());
        addPacketPrototype((packet = new CM_DISCONNECT((byte) 0xED)),packet.getValidState());
        addPacketPrototype((packet = new CM_L2AUTH_LOGIN_CHECK((byte)0x08)),packet.getValidState());
        addPacketPrototype((packet = new CM_MAC_ADDRESS((byte)0x30)),packet.getValidState());
        addPacketPrototype((packet = new CM_CHARACTER_LIST((byte)0x09)),packet.getValidState());
    }

    static {
        //载入封包
        loadAllPacket();
    }


    private static class SingletonHolder{
        private static final ClientPacketFactory instance = new ClientPacketFactory();
    }

    public static ClientPacketFactory getInstance(){
        return SingletonHolder.instance;
    }
}
