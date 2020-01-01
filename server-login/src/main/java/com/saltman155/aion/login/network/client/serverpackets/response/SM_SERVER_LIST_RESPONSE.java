package com.saltman155.aion.login.network.client.serverpackets.response;

import com.saltman155.aion.login.MainServerManager;
import com.saltman155.aion.login.config.spring.SpringContext;
import com.saltman155.aion.login.model.MainServerInfo;
import com.saltman155.aion.login.model.entity.Account;
import com.saltman155.aion.login.network.client.ClientChannelAttr;
import com.saltman155.aion.login.network.client.ServerPacket;
import com.saltman155.aion.login.utils.ChannelUtil;
import io.netty.channel.Channel;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;

/**
 * 对客户端 ${@link com.saltman155.aion.login.network.client.clientpackets.request.CM_SERVER_LIST_REQUEST}
 * 封包的响应封包，返回登录服务器的各种信息
 * @author saltman155
 * @date 2019/11/16 18:07
 */

public class SM_SERVER_LIST_RESPONSE extends ServerPacket {

    private static final byte OPCODE = 0x04;

    private Channel userConnection;

    private Map<Integer,Integer> characterCounts;

    public SM_SERVER_LIST_RESPONSE(Channel userConnection, Map<Integer,Integer> characterCounts) {
        super(OPCODE);
        this.userConnection = userConnection;
        this.characterCounts = characterCounts;
    }

    @Override
    protected void appendBody(ByteBuffer buf) {
        Account user = userConnection.attr(ClientChannelAttr.ACCOUNT).get();
        Collection<MainServerInfo> servers =
                SpringContext.getContext().getBean(MainServerManager.class).getGameServers();
        int maxId = 0;
        //写入游戏服务器数量
        buf.put((byte) servers.size());
        //写入最后一次登录服务器id
        buf.put(user.getLastServer());
        for(MainServerInfo server : servers){
            maxId = Math.max(server.getId(),maxId);
            //1个字节 写入服务器id
            buf.put(server.getId());
            //1个字节 写入服务器ip
            buf.put(server.getIpAddressForPlayerIp(ChannelUtil.getIp(userConnection)));
            //8个字节 写入服务器port
            buf.putInt(server.getClientPort());
            //1个字节 age limit
            buf.put((byte) 0x00);
            //1个字节 pvp=1
            buf.put((byte) 0x01);
            //4个字节 当前在线人数
            buf.putShort((short) server.getCurrentPlayers());
            //4个字节 最大在线人数
            buf.putShort((short) server.getMaxPlayers());
            //1个字节 是否在线
            buf.put((byte) (server.isOnline() ? 0x01 : 0x00));
            buf.putInt(0x00000000);
            //server.brackets ? 0x01 : 0x00
            buf.put((byte) 0x00);
        }
        buf.putShort((short) (maxId + 1));
        buf.put((byte) 0x01);
        //写入每个服务器的用户数量
        for(int i = 1;i< maxId;i++){
            Integer count = characterCounts.get(i);
            buf.put((byte) (count == null ? 0 : count));
        }
    }
}
