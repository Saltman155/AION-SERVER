package com.saltman155.aion.commons.properties.transformers;

import com.saltman155.aion.commons.properties.PropertyTransformer;

import java.lang.reflect.Field;

/**
 * @author: saltman155
 * @date: 2019/3/14 23:45
 */

public class StringTransformer implements PropertyTransformer<String> {

    /**维护一个实例*/
    public static final StringTransformer INSTANCE = new StringTransformer();

    /**
     * String类型的直接返回
     * @param value     字符串值
     * @param field     实际值的类型
     * @return          相应的值
     */
    @Override
    public String transform(String value, Field field) {
        return value;
    }
}
