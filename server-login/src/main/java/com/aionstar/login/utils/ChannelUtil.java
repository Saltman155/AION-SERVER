package com.aionstar.login.utils;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

/**
 * channel的一些简单操作
 * @author saltman155
 * @date 2019/10/23 3:08
 */

public class ChannelUtil {

    public static String getIp(Channel channel){
        return ((SocketChannel)channel).remoteAddress().getAddress().getHostAddress();
    }

}
