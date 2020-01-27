package com.aionstar.commons.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * channel的一些简单操作
 * @author saltman155
 * @date 2019/10/23 3:08
 */

public class ChannelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ChannelUtil.class);

    public static String getIp(Channel channel){
        return ((SocketChannel)channel).remoteAddress().getAddress().getHostAddress();
    }

    public static void close(Channel channel,Object packet){
        try {
            if(packet != null) {
                channel.writeAndFlush(packet).sync();
            }
            channel.close();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 向buf中写入字符串固定格式
     * 在与客户端通讯协议中，字符串传递是固定的 unicode编码 + /000 结尾
     *  你可能会注意到我用的是writeShortLE而不是writeChar，因为这狗日的通讯协议是小端编码，而ByteBuf没有对
     *  writeChar做小端处理，好在unicode编码都是两个字节，所以用writeShortLE可以实现一样的效果
     * @param buf
     * @param s
     */
    public static void bufWriteS(ByteBuf buf,String s){
        if (s != null) {
            final int len = s.length();
            for (int i = 0; i < len; i++) {
                buf.writeShortLE(s.charAt(i));
            }
        }
        buf.writeShortLE('\u0000');
    }

    public static String bufReadS(ByteBuf buf){
        StringBuilder sb = new StringBuilder();
        try{
            char c;
            while((c = (char) buf.readShort()) != '\u0000'){
                sb.append(c);
            }
        }catch (Exception e){
            logger.error("读取字符串数据失败！");
        }
        return sb.toString();
    }

}
