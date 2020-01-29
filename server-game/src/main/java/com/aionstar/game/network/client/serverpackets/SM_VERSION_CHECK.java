package com.aionstar.game.network.client.serverpackets;

import com.aionstar.game.config.NetworkConfigure;
import com.aionstar.game.config.ServerConfigure;
import com.aionstar.game.config.network.PlayerIPConfig;
import com.aionstar.game.network.client.AionServerPacket;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 这个数据包是对客户端发来的请求数据包 ${@link com.aionstar.game.network.client.clientpackets.CM_VERSION_CHECK} 的响应
 * 响应中可以设置 是否是当前的版本
 * 是否是当前的状态
 */
public class SM_VERSION_CHECK extends AionServerPacket {

    private static final Logger logger = LoggerFactory.getLogger(SM_VERSION_CHECK.class);

    private static final byte OPCODE = (byte) 0xFC;

    private int version;

    public SM_VERSION_CHECK(int version) {
        super(OPCODE);
        this.version = version;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        logger.info("写入版本信息...");
        buf.writeByte(0x00);                                // 版本符合
        buf.writeByte(NetworkConfigure.LS_REGISTER_ID);     // 服务端编号
        buf.writeIntLE(0x000188AD);                         // unk (start year month day ?)
        buf.writeIntLE(0x000188A6);                         // unk (start year month day ?)
        buf.writeIntLE(0x00000000);                         // 间隔符
        buf.writeIntLE(0x00018898);                         // unk (year month day ?)
        buf.writeIntLE(0x4C346D9D);                         // unk (start server time in ms ?)
        buf.writeByte(0x00);                                // unk
        buf.writeByte(ServerConfigure.SERVER_COUNTRY_CODE); // 国家编码
        buf.writeByte(0x00);                                // unk
        //TODO 这个是游戏角色创建的限制 比较复杂，等下看
        buf.writeByte(0x01);
        buf.writeIntLE((int) (System.currentTimeMillis() / 1000));  //当前服务端时间
        buf.writeShortLE(0x015E);       // unk
        buf.writeShortLE(0x0A01);       // unk
        buf.writeShortLE(0x0A01);       // unk
        buf.writeShortLE(0x020A);       // unk
        //TODO 从这里开始，1.9的源码和3.6的源码开始长度不一样了，我也不知道该参照哪个
        // 权且先按照1.9的复制粘贴一番
        buf.writeByte(0x00);
        buf.writeByte(0x01);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeBytes(new byte[]{127,0,0,1});         //聊天服务器ip
        buf.writeShortLE(10241);                                    //聊天服务器端口
    }

}
