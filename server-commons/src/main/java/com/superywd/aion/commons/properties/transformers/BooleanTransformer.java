package com.superywd.aion.commons.properties.transformers;

import com.superywd.aion.commons.properties.PropertyTransformer;

import java.lang.reflect.Field;

/**
 * @author: 迷宫的中心
 * @date: 2019/2/28 23:41
 */

public class BooleanTransformer implements PropertyTransformer {

    /**维护一个实例*/
    public static final BooleanTransformer INSTANCE = new BooleanTransformer();

    @Override
    public Object transform(String value, Field field) {
        return null;
    }
}
