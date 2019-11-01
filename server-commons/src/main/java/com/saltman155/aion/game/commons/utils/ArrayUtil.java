package com.saltman155.aion.game.commons.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author: saltman155
 * @date: 2019/4/13 11:17
 */
public class ArrayUtil {


    /**
     * 判断一个集合&映射&数组是不是空的
     * @param array     需要判断的对象
     * @return          是不是空的
     */
    public static boolean isEmpty(Object array){
        if(array == null){
            return true;
        }
        if(array instanceof Collection){
            return ((Collection) array).isEmpty();
        }
        if(array.getClass().isArray()){
            return Array.getLength(array) == 0;
        }
        if(array instanceof Map){
            return ((Map)array).isEmpty();
        }
        throw new IllegalArgumentException("不支持的判断类型:" + array.getClass().getName());
    }
}
