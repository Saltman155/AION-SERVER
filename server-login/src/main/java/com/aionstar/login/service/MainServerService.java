package com.aionstar.login.service;

import com.aionstar.commons.utils.NetworkUtil;
import com.aionstar.login.dao.MainServerMapper;
import com.aionstar.login.exception.MSAlreadyRegisterException;
import com.aionstar.login.model.MainServerInfo;
import com.aionstar.login.model.configure.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 主服务器业务处理类
 * @author saltman155
 * @date 2020/1/18 22:57
 */

@Service
public class MainServerService {

    private static final Logger logger = LoggerFactory.getLogger(MainServerService.class);

    private final MainServerMapper mainServerMapper;

    private Map<Byte, MainServerInfo> mainServerInfoMap;

    @Autowired
    public MainServerService(MainServerMapper mainServerMapper) {
        this.mainServerMapper = mainServerMapper;
    }


    @PostConstruct
    private void loadMainServer(){
        logger.info("开始载入游戏主服务器配置...");
        List<MainServerInfo> serverList = mainServerMapper.getAllMainServer();
        mainServerInfoMap = new HashMap<>();
        for(MainServerInfo gameServer : serverList){
            logger.info("载入名称为 {} ,IP为 {} 的主服务器配置...",gameServer.getName(),gameServer.getIp());
            mainServerInfoMap.put(gameServer.getId(),gameServer);
        }
        logger.info("共载入了 {} 个游戏主服务器配置！",mainServerInfoMap.size());
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

    /**
     * 注册主服务器（更新相关的类）
     * @param info  待注册的主服务器
     * @throws MSAlreadyRegisterException   如果待注册的主服务器已经注册，则抛出该异常
     */
    public synchronized void registerServer(MainServerInfo info) throws MSAlreadyRegisterException{
        MainServerInfo old = mainServerInfoMap.get(info.getId());
        //老配置没有相关的连接或连接是关闭的，说明没有注册，直接安排上
        if(old == null || old.getLoginConnection() == null || !old.getLoginConnection().isActive()){
            mainServerInfoMap.put(info.getId(),info);
        }
        //否则说明已经注册
        else{
            throw new MSAlreadyRegisterException(info.getId(),info.getName());
        }
    }

    /**
     * 获取所有的游戏服务器列表
     * @return  游戏服务器列表
     */
    public Collection<MainServerInfo> getGameServers(){
        return Collections.unmodifiableCollection(mainServerInfoMap.values());
    }

}
