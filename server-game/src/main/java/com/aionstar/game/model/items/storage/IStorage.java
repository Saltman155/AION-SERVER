package com.aionstar.game.model.items.storage;


import com.aionstar.game.model.entity.Player;

/**
 * 存储型类的公共接口
 */

public interface IStorage {

    /**
     * 获取这个存储类具体的类型
     * @return      具体类型
     */
    StorageType getStorageType();

    /**
     * 设置存储的所有者
     * @param player    所有者
     */
    void setOwner(Player player);

    /**
     * 获取该存储类型存放的基纳数量
     * @return          存储的基纳数量
     */
    long getKinah();

    /**
     * 获取该存储类型存放的基纳物品对象
     * 如果这个存储类型不能放基纳，则返回 null
     * @return         存储基纳对象
     */
    Item getKinahItem();
}
