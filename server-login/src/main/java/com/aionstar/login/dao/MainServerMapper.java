package com.aionstar.login.dao;

import com.aionstar.login.model.MainServerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author saltman155
 * @date 2019/11/16 16:28
 */

@Repository
public interface MainServerMapper {

    /**
     * 获取所有的游戏服务器信息
     * @return 游戏服务器信息
     */
    List<MainServerInfo> getAllMainServer();

    /**
     * 按照游戏服务器id获取游戏服务器
     * @param id    游戏服务器id
     * @return      游戏服务器信息
     */
    MainServerInfo getMainServerById(@Param("id")byte id);

}
