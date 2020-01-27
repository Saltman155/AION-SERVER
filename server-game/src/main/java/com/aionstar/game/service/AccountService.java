package com.aionstar.game.service;

import com.aionstar.game.model.account.Account;
import com.aionstar.game.model.account.AccountTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 账号服务管理
 */

public class AccountService {

    public static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public static Account getAccount(int accountId, String accountName, AccountTime accountTime,
                                     byte accessLevel,byte membership,long toll){
        logger.debug("请求载入账号 {} : {}",accountId,accountName);
        return null;
    }

}
