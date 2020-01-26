package com.aionstar.login.service;

import com.aionstar.login.config.datasource.DaoManager;
import com.aionstar.login.dao.BannedMacDao;
import com.aionstar.commons.network.model.BannedMacEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author saltman155
 * @date 2020/1/18 22:14
 */

public class AccountBannedService {

    public static Map<String, BannedMacEntry> getAllMacBand(){
        List<BannedMacEntry> list = DaoManager.getDao(BannedMacDao.class).selectAll();
        Map<String,BannedMacEntry> result = new HashMap<>();
        for(BannedMacEntry item : list ){
            result.put(item.getMac(),item);
        }
        return result;
    }
}
