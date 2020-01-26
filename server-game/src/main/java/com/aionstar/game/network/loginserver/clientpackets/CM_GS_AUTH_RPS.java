package com.aionstar.game.network.loginserver.clientpackets;

import com.aionstar.commons.network.BaseChannelAttr;
import com.aionstar.commons.network.packet.ClientPacket;
import com.aionstar.game.controller.NetworkController;
import com.aionstar.game.network.loginserver.LSChannelAttr;
import com.aionstar.game.network.loginserver.serverpackets.SM_GS_AUTH;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 对应登录服务器发来的校验结果数据包
 * ${@link com.aionstar.login.network.mainserver.serverpackets.SM_GS_AUTH_RPS}
 * @author saltman155
 * @date 2020/1/19 0:40
 */

public class CM_GS_AUTH_RPS extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(CM_GS_AUTH_RPS.class);

    public CM_GS_AUTH_RPS(byte opcode, Channel channel, ByteBuf data) {
        super(opcode, channel, data);
    }

    private byte response;

    private byte serverCount;


    @Override
    protected void handler() {
        //验证成功
        if(response == 0x00){
            logger.info("与登录服务器的连接验证成功！");
            channel.attr(LSChannelAttr.LS_SESSION_STATE).set(BaseChannelAttr.InnerSessionState.AUTHED);
            //TODO 这里有一个步骤非常关键，目前没有做，连接上后，需要将游戏服务器上的角色信息发送给
            // 登录服务器（如果登录服务器是异常终止的），帮助登录服务器恢复终止前的状态
            //保存游戏服务器数量（不知道有什么用）
            NetworkController.updateServerCount(serverCount);
        }
        //验证失败 账号&密码错误
        if(response == 0x01){
            logger.error("与登录服务器的连接验证失败！错误的验证信息...");
            try {
                channel.close().sync();
                System.exit(-1);
            } catch (Exception ignore) { }
        }
        //验证失败 已经注册的服务器
        if(response == 0x02){
            logger.error("与登录服务器的连接验证失败！已经注册的服务器...");
            //10秒后再尝试发一个连接请求
            channel.eventLoop().schedule(() -> {
                channel.writeAndFlush(new SM_GS_AUTH());
            },10000, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    protected void readData() {
        response = data.readByte();
        if(response == 0){
            serverCount = data.readByte();
        }
    }
}
