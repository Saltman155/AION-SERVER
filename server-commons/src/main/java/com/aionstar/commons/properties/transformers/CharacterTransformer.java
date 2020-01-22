package com.aionstar.commons.properties.transformers;

import com.aionstar.commons.properties.PropertyTransformer;

import java.lang.reflect.Field;

/**
 * @author: saltman155
 * @date: 2019/2/28 23:41
 */

public class CharacterTransformer implements PropertyTransformer {

    /**维护一个实例*/
    public static final CharacterTransformer INSTANCE = new CharacterTransformer();

    @Override
    public Object transform(String value, Field field) {
        return null;
    }

}
