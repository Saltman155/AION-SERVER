package com.aionstar.game.network.loginserver.serverpackets;

import com.aionstar.commons.network.model.IPRange;
import com.aionstar.commons.network.packet.ServerPacket;
import com.aionstar.game.model.configure.ClientNetwork;
import com.aionstar.game.model.configure.LoginNetwork;
import com.aionstar.game.model.configure.main.ServerConfigure;
import com.aionstar.game.model.configure.network.PlayerIPConfig;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * 游戏主服务端与登录服务端连接后，发送给登录服务端的验证信息
 * @author saltman155
 * @date 2020/1/18 17:07
 */

public class SM_GS_AUTH extends ServerPacket {

    private static final byte opcode = 0x00;

    public SM_GS_AUTH() {
        super(opcode);
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        //在登录服务器上登记的id
        buf.writeByte(LoginNetwork.getInstance().getServerId());
        buf.writeByte(PlayerIPConfig.getInstance().getDefaultAddress().length);
        buf.writeBytes(PlayerIPConfig.getInstance().getDefaultAddress());
        List<IPRange> ranges = PlayerIPConfig.getInstance().getRanges();
        int size = ranges.size();
        buf.writeIntLE(size);
        for (int i = 0; i < size; i++) {
            IPRange ipRange = ranges.get(i);
            byte[] min = ipRange.getMinAsByteArray();
            byte[] max = ipRange.getMaxAsByteArray();
            buf.writeByte(min.length);
            buf.writeBytes(min);
            buf.writeByte(max.length);
            buf.writeBytes(max);
            buf.writeByte(ipRange.getAddress().length);
            buf.writeBytes(ipRange.getAddress());
        }
        //主服务器提供游戏客户端连接的端口
        buf.writeShortLE(ClientNetwork.getInstance().getPort());
        //主服务器最大在线人数
        buf.writeIntLE(ServerConfigure.getInstance().getMaxOnlinePlayers());
        String password = LoginNetwork.getInstance().getServerPassword();
        //登录密码
        if(StringUtils.isBlank(password)){
            buf.writeByte(0x00);
            return;
        }
        byte[] bytes = password.getBytes(CharsetUtil.UTF_8);
        buf.writeByte(bytes.length);
        buf.writeBytes(bytes);
    }


}
