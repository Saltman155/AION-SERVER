package com.saltman155.aion.login;

import com.saltman155.aion.login.dao.GameServerMapper;
import com.saltman155.aion.login.model.GameServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 游戏服务器管理器
 * @author saltman155
 * @date 2019/11/1 23:29
 */

@Component
public class GameServerManager {

    private static final Logger logger = LoggerFactory.getLogger(GameServerManager.class);

    @Resource
    private GameServerMapper gameServerMapper;

    private Map<Byte, GameServerInfo> gameServerInfoMap;

    public void init(){
        List<GameServerInfo> serverList = gameServerMapper.getAllGameServer();
        gameServerInfoMap = new HashMap<>();
        for(GameServerInfo gameServer : serverList){
            gameServerInfoMap.put(gameServer.getId(),gameServer);
        }
        logger.info("载入了 {} 个游戏主服务器...",gameServerInfoMap.size());
    }

    /**
     * 获取所有的游戏服务器列表
     * @return  游戏服务器列表
     */
    public Collection<GameServerInfo> getGameServers(){
        return Collections.unmodifiableCollection(gameServerInfoMap.values());
    }

}
