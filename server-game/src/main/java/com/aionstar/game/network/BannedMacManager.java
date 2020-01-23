package com.aionstar.game.network;

import com.aionstar.commons.network.model.BannedMacEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mac地址封禁管理器
 *  该类中维护着所有的mac封禁的详细信息
 * @author saltman155
 * @date 2020/1/23 21:41
 */

public class BannedMacManager {

    private static final Logger logger = LoggerFactory.getLogger(BannedMacManager.class);

    /**存放bannedMac信息*/
    private Map<String, BannedMacEntry> bannedMap = new ConcurrentHashMap<>();

    private BannedMacManager(){}

    /**
     * 封禁一个mac地址
     * @param address   待封禁的地址
     * @param newTime   封禁到期的时间
     * @param details   封禁原因
     */
    public final void banAddress(String address, long newTime, String details){
        //TODO 这里要发包给客户端，更新封禁数据到数据库里去
    }

    /**
     * 解封一个mac地址
     * @param address   待解封的地址
     * @param details   解封原因
     */
    public final void unbanAddress(String address,String details){
        //TODO 这里要删除封禁表里的数据，并发包给登录服务器通知删除数据库记录
    }

    /**
     * 判断一个mac地址是否被封禁
     * @param address   待判断的地址
     * @return          是否被封禁
     */
    public final boolean isBanned(String address){
        if (bannedMap.containsKey(address)) {
            return this.bannedMap.get(address).isActive();
        } else {
            return false;
        }
    }

    /**
     * 将从登录服务器获取的封禁信息添加到封禁表里去
     * @param item     封禁信息
     */
    public final void dbLoad(BannedMacEntry item) {
        this.bannedMap.put(item.getMac(), item);
    }


    private static class SingletonHolder{
        private static final BannedMacManager instance = new BannedMacManager();
    }

    public static BannedMacManager getInstance(){
        return SingletonHolder.instance;
    }

}
