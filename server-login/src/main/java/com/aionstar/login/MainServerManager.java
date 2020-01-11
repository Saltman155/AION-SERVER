package com.aionstar.login;

import com.aionstar.login.dao.MainServerMapper;
import com.aionstar.login.model.MainServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * 游戏服务器管理器
 * @author saltman155
 * @date 2019/11/1 23:29
 */

@Component
public class MainServerManager {

    private static final Logger logger = LoggerFactory.getLogger(MainServerManager.class);

    @Resource
    private MainServerMapper mainServerMapper;

    private Map<Byte, MainServerInfo> mainServerInfoMap;

    @PostConstruct
    public void init(){
        logger.info("开始载入游戏主服务器配置...");
        List<MainServerInfo> serverList = mainServerMapper.getAllGameServer();
        mainServerInfoMap = new HashMap<>();
        for(MainServerInfo gameServer : serverList){
            logger.info("载入名称为 {} ,IP为 {} 的主服务器配置...",gameServer.getName(),gameServer.getIp());
            mainServerInfoMap.put(gameServer.getId(),gameServer);
        }
        logger.info("共载入了 {} 个游戏主服务器配置！",mainServerInfoMap.size());
    }

    /**
     * 获取所有的游戏服务器列表
     * @return  游戏服务器列表
     */
    public Collection<MainServerInfo> getGameServers(){
        return Collections.unmodifiableCollection(mainServerInfoMap.values());
    }

}
