package com.aionstar.login.network.mainserver.clientpackets;

import com.aionstar.commons.network.model.IPRange;
import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.login.controller.AccountBannedController;
import com.aionstar.login.controller.MainServerController;
import com.aionstar.login.model.BannedMacEntry;
import com.aionstar.login.network.mainserver.MSAuthResponse;
import com.aionstar.login.network.mainserver.MSChannelAttr;
import com.aionstar.login.network.mainserver.serverpackets.SM_BAN_MAC_LIST;
import com.aionstar.login.network.mainserver.serverpackets.SM_GS_AUTH_RPS;
import com.aionstar.login.service.MainServerService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 该数据包对应主服务端发送的数据包 ${@link com.aionstar.game.network.loginserver.serverpackets.SM_GS_AUTH}
 * 读取主服务端发送的验证信息并加以一通解析
 * @author saltman155
 * @date 2020/1/18 18:40
 */

public class CM_GS_AUTH extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_GS_AUTH.class);

    private static final byte OPCODE = 0x00;

    private byte serverId;

    private String password;

    private int maxPlayers;

    private int port;

    private byte[] defaultAddress;

    private List<IPRange> ranges;

    public CM_GS_AUTH(Channel channel, ByteBuf data) {
        super(OPCODE, channel, data);
    }

    @Override
    protected void handler() {
        MSAuthResponse rps = MainServerController
                .registerMainServer(channel,serverId,password,defaultAddress,ranges,port,maxPlayers);
        //验证通过的话，就进行一些操作
        if(rps.equals(MSAuthResponse.AUTHED)){
            logger.info("已与游戏主服务器 #{} 建立连接.",serverId);
            channel.attr(MSChannelAttr.M_SESSION_STATE).set(MSChannelAttr.InnerSessionState.AUTHED);
            int size = MainServerService.getGameServers().size();
            channel.writeAndFlush(new SM_GS_AUTH_RPS(rps,size));
            //并在500毫秒后发送一个 banned MAC 的名单给主服务端
            Map<String, BannedMacEntry> bandMap = AccountBannedController.getAllMacBand();
            channel.eventLoop().schedule(() -> {
                channel.writeAndFlush(new SM_BAN_MAC_LIST(bandMap));
            },500, TimeUnit.MILLISECONDS);
        }else{
            //否则发送失败的数据包
            channel.writeAndFlush(new SM_GS_AUTH_RPS(rps));
        }

    }

    @Override
    protected void readData() {
        int size;
        serverId = data.readByte();
        size = data.readByte();
        defaultAddress = new byte[size];
        data.readBytes(defaultAddress,0,size);
        size = data.readIntLE();
        ranges = new ArrayList<>(size);
        //咔咔一通读就完事了
        for(int i = 0 ;i < size; i++){
            byte[] min,max,address;
            size = data.readByte();
            min = new byte[size];
            data.readBytes(min,0,size);
            size = data.readByte();
            max = new byte[size];
            data.readBytes(max,0,size);
            size = data.readByte();
            address = new byte[size];
            data.readBytes(address,0,size);
            ranges.add(new IPRange(min,max,address));
        }
        port = data.readShortLE();
        maxPlayers = data.readIntLE();
        //读取密码
        size = data.readByte();
        if(size > 0){
            byte[] p = new byte[size];
            data.readBytes(p,0,size);
            password = new String(p, CharsetUtil.UTF_8);
        }
    }
}
