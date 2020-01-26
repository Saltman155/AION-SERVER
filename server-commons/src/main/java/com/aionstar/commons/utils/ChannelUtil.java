package com.aionstar.commons.utils;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
