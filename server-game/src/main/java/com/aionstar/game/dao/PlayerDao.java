package com.aionstar.game.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author saltman155
 * @date 2019/10/24 2:56
 */

public interface PlayerDao {

    /**
     * 获取指定账号在该服务器上的玩家数量
     * @param accountId     账号
     * @return              玩家数量
     */
    Integer getPlayerCount(@Param("accountId")Integer accountId);

}
