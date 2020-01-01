package com.saltman155.aion.commons.properties.transformers;

import com.saltman155.aion.commons.properties.PropertyTransformer;

import java.lang.reflect.Field;

/**
 * @author: saltman155
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
