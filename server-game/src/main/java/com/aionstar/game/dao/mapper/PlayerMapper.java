package com.aionstar.game.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author saltman155
 * @date 2020/1/23 22:15
 */

public interface PlayerMapper {

    /**
     * 获取指定账号在该服务器上的玩家数量
     * @param accountId     账号
     * @return              玩家数量
     */
    Integer getPlayerCount(@Param("accountId")Integer accountId);

}
