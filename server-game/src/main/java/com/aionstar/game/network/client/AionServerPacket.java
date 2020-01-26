package com.aionstar.game.network.client;

import com.aionstar.commons.network.packet.ServerPacket;
import com.aionstar.game.network.crypt.ClientCrypt;
import io.netty.buffer.ByteBuf;

/**
 * 主服务端发送给客户端的封包
 * 与 ${@link ServerPacket } 没有区别，唯一的区别就是对部分方法进行了重写
 * @author saltman155
 * @date 2020/1/24 1:00
 */

public abstract class AionServerPacket extends ServerPacket {

    /**
     * 数据包操作符构造packet
     * 注意，这里的数据包操作符最终也没有直接写入，而是会进行相关的混淆，
     * 具体的细节可以看 ${@link AionServerPacket#writeOP(ByteBuf, int)}  }
     * */

    public AionServerPacket(byte opcode) {
        super(opcode);
    }


    @Override
    public void writeData(ByteBuf buf){
        //用下面那个带crypt参数的方式进行写数据，顺带加密
        throw new UnsupportedOperationException();
    }

    /**
     * 向数据包中一通写出（还有加密操作）
     * @param buf       连接的临时写出空间
     * @param crypt     加密器
     */
    public void writeAndEncryptData(ByteBuf buf,ClientCrypt crypt) {
        buf.clear();
        //跳过头两个字节
        buf.writerIndex(2);
        // 写入操作数
        writeOP(buf,getOpcode());
        // 写入具体数据
        appendBody(buf);
        // 写入包长
        buf.setShortLE(0,buf.writerIndex());
        // 将数据域一通加密
        crypt.encrypt(buf.slice(2,buf.writerIndex()));
    }

    /**
     * 向数据包中写入该数据包的操作数（看样子是固定三个字节）
     * @param buf       数据包
     * @param opcode    操作数
     */
    public void writeOP(ByteBuf buf,int opcode){
        //做个混淆
        byte op = (byte)((opcode + 0xAE) ^ 0xEE);
        buf.writeByte(op);
        buf.writeByte(ClientCrypt.STATIC_SERVER_PACKET_CODE);
        //这个估计是校验？
        buf.writeByte(~op);
    }

}
