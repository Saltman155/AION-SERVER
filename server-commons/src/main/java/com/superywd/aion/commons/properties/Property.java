package com.superywd.aion.commons.properties;

/**
 * @author: 迷宫的中心
 * @date: 2019/1/24 00:41
 */

public @interface Property {

    String key() default "";

    String defaultValue() default "";

    Class<? extends PropertyTransformer> propertyTransformer() default PropertyTransformer.class;
}
