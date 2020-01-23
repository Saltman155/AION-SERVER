package com.aionstar.login.dao.mapper;

import com.aionstar.login.model.BannedMacEntry;

import java.util.List;

/**
 * @author saltman155
 * @date 2020/1/18 22:06
 */

public interface BannedMacMapper {

    List<BannedMacEntry> selectAll();

}
