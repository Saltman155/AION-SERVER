package com.aionstar.game.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author saltman155
 * @date 2020/1/23 19:07
 */

public class LoggerUtil {

    private static final Logger simpleLogger = LoggerFactory.getLogger("simpleLogger");

    public static void simpleShow(String str,Object... params){
        simpleLogger.info(str,params);
    }
}
