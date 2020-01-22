package com.aionstar.login.service;

import com.aionstar.commons.utils.NetworkUtil;
import com.aionstar.login.dao.MainServerMapper;
import com.aionstar.login.model.MainServerInfo;
import com.aionstar.login.model.configure.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author saltman155
 * @date 2020/1/18 22:57
 */

@Service
public class MainServerService {

    private static final Logger logger = LoggerFactory.getLogger(MainServerService.class);

    private final MainServerMapper mainServerMapper;

    @Autowired
    public MainServerService(MainServerMapper mainServerMapper) {
        this.mainServerMapper = mainServerMapper;
    }

    /**
     * 检查服务端连接的注册信息是否正确
     * @param serverId      服务端连接id
     * @param password      连接密码
     * @param ip            服务端ip
     * @return              当验证成功时，返回服务端信息，否则返回null
     */
    public MainServerInfo mainServerRegisterCheck(byte serverId,String password,String ip){
        MainServerInfo info = mainServerMapper.getMainServerById(serverId);
        if(info == null){
            logger.warn("来自ip {} 请求了不存在的serverId的游戏服务端 {} 并尝试用密码 {} 连接，验证失败!",
                    ip,serverId,password);
            return null;
        }
        if(info.getPassword().equals(password) ||
           NetworkUtil.checkIPMatching(info.getIp(),ip)){
            return info;
        }else{
            logger.warn("来自ip {}, serverId为 {} 的游戏服务端尝试用密码 {} 连接，验证失败!",
                    ip,serverId,password);
            return null;
        }

    }
}
