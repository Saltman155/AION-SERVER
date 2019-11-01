package com.saltman155.aion.game.commons.utils;

/**
 * 字符串操作类
 * @author: saltman155
 * @date: 2019/4/17 14:27
 */
public class StringUtil {


    public static boolean isIdle(String string){
        return string == null || "".equals(string.trim());
    }

}
