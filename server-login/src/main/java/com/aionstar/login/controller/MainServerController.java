package com.aionstar.login.controller;

import com.aionstar.commons.network.model.IPRange;
import com.aionstar.login.MainServerManager;
import com.aionstar.login.exception.MSAlreadyRegisterException;
import com.aionstar.login.model.MainServerInfo;
import com.aionstar.login.network.mainserver.MSAuthResponse;
import com.aionstar.login.service.MainServerService;
import com.aionstar.login.utils.ChannelUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 主服务器连接控制器
 * @author saltman155
 * @date 2020/1/18 21:30
 */

@Controller
public class MainServerController {

    private static final Logger logger = LoggerFactory.getLogger(MainServerController.class);

    private final MainServerService mainServerService;

    private final MainServerManager mainServerManager;

    @Autowired
    public MainServerController(MainServerService mainServerService,
                                MainServerManager mainServerManager) {
        this.mainServerService = mainServerService;
        this.mainServerManager = mainServerManager;
    }


    /**
     * 注册游戏主服务器到登录服务器
     * @param channel               连接通道
     * @param serverId              服务器id
     * @param password              服务器密码
     * @param defaultAddress        默认地址
     * @param ranges                可连接ip范围
     * @param port                  主服务器端口
     * @param maxPlayers            主服务器最大在线人数
     * @return                      是否注册成功
     */
    public MSAuthResponse registerMainServer(
            Channel channel, byte serverId, String password, byte[] defaultAddress,
            List<IPRange> ranges,int port,int maxPlayers){
        MainServerInfo info;
        try {
            info = mainServerService.mainServerRegisterCheck(serverId,password, ChannelUtil.getIp(channel));
            if(info == null){ return MSAuthResponse.NOT_AUTHED; }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return MSAuthResponse.NOT_AUTHED;
        }
        info.setClientPort(port);
        info.setMaxPlayers(maxPlayers);
        info.setDefaultAddress(defaultAddress);
        info.setIpRanges(ranges);
        info.setLoginConnection(channel);
        //验证通过则进行注册
        try {
            mainServerManager.registerServer(info);
            return MSAuthResponse.AUTHED;
        } catch (MSAlreadyRegisterException e) {
            logger.error(e.getMessage());
            return MSAuthResponse.ALREADY_REGISTER;
        }
    }
}
