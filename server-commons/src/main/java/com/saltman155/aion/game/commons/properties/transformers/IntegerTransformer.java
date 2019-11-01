package com.saltman155.aion.game.commons.properties.transformers;

import com.saltman155.aion.game.commons.properties.PropertyTransformer;
import com.saltman155.aion.game.commons.properties.exception.TransformationException;

import java.lang.reflect.Field;

/**
 * @author: saltman155
 * @date: 2019/2/28 23:42
 */

public class IntegerTransformer implements PropertyTransformer {

    /**维护一个实例*/
    public static final IntegerTransformer INSTANCE = new IntegerTransformer();

    @Override
    public Object transform(String value, Field field) {
        try{
            return Integer.parseInt(value);
        }catch (Exception e){
            throw new TransformationException(e);
        }
    }
}
