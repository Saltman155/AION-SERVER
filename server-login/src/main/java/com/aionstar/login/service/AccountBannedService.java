package com.aionstar.login.service;

import com.aionstar.login.dao.BannedMacMapper;
import com.aionstar.login.model.BannedMacEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author saltman155
 * @date 2020/1/18 22:14
 */

@Service
public class AccountBannedService {

    private final BannedMacMapper bannedMacMapper;

    @Autowired
    public AccountBannedService(BannedMacMapper bannedMacMapper) {
        this.bannedMacMapper = bannedMacMapper;
    }

    public Map<String, BannedMacEntry> getAllMacBand(){
        List<BannedMacEntry> list = bannedMacMapper.selectAll();
        Map<String,BannedMacEntry> result = new HashMap<>();
        for(BannedMacEntry item : list ){
            result.put(item.getMac(),item);
        }
        return result;
    }
}
