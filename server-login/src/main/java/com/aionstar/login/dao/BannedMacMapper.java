package com.aionstar.login.dao;

import com.aionstar.login.model.BannedMacEntry;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author saltman155
 * @date 2020/1/18 22:06
 */

@Repository
public interface BannedMacMapper {

    List<BannedMacEntry> selectAll();

}
