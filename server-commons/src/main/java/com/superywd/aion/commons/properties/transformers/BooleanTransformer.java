package com.superywd.aion.commons.properties.transformers;

import com.superywd.aion.commons.properties.PropertyTransformer;

import java.lang.reflect.Field;

/**
 * @author: saltman155
 * @date: 2019/2/28 23:41
 */

public class BooleanTransformer implements PropertyTransformer<Boolean> {

    /**整数的正则匹配式*/
    private static final String NUM_REGEX = "^-?[0-9]{1,9}$";
    /**维护一个实例*/
    public static final BooleanTransformer INSTANCE = new BooleanTransformer();



    @Override
    public Boolean transform(String value, Field field) {
        if ("true".equalsIgnoreCase(value)){
            return true;
        }
        if("false".equalsIgnoreCase(value)){
            return false;
        }
        if(value.matches(NUM_REGEX)){
            Long num = Long.valueOf(value);
            return num > 0;
        }
        throw new RuntimeException("错误的值 " + value + " ，无法正确被转换");
    }
}
