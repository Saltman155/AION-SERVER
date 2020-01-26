package com.aionstar.login.dao.mapper;

import com.aionstar.login.model.entity.AccountTime;
import org.apache.ibatis.annotations.Param;

public interface AccountTimeMapper {

    AccountTime getAccountTime(@Param("accountId")Integer accountId);

}
