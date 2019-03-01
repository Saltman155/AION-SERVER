package com.superywd.aion.commons.properties.transformers;

import com.superywd.aion.commons.properties.PropertyTransformer;

import java.lang.reflect.Field;

/**
 * @author: 迷宫的中心
 * @date: 2019/2/28 23:41
 */

public class ByteTransformer implements PropertyTransformer {

    /**维护一个实例*/
    public static final ByteTransformer INSTANCE = new ByteTransformer();

    @Override
    public Object transform(String value, Field field) {
        return null;
    }

}
