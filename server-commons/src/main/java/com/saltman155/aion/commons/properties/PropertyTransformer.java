package com.saltman155.aion.commons.properties;

import java.lang.reflect.Field;

/**
 * @author: saltman155
 * @date: 2019/1/24 00:41
 */

public interface PropertyTransformer<T> {

    /**
     * 转换方法，这个方法将字符串值转换为实际的值
     * @param value     字符串值
     * @param field     实际值的类型
     * @return          转换后的值
     */
    T transform(String value, Field field);
}
