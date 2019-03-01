package com.superywd.aion.commons.properties;

import java.lang.annotation.*;

/**
 * @author: 迷宫的中心
 * @date: 2019/1/24 00:41
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    /**
     * defaultValue取的默认的值，当取此值时，表示
     */
    String DEFAULT_VALUE = "DO_NOT_OVERWRITE_INITIALIZATION_VALUE";

    /**
     * 映射到配置文件中的键名
     * @return  键名
     */
    String key();

    /**
     * 配置的默认的值
     * @return  默认值
     */
    String defaultValue() default "";

    /**
     * 键的具体类型
     * {@link Boolean }     对应于 {@link com.superywd.aion.commons.properties.transformers.BooleanTransformer}
     * {@link Byte}         对应于 {@link com.superywd.aion.commons.properties.transformers.ByteTransformer}
     * {@link Character}    对应于 {@link com.superywd.aion.commons.properties.transformers.CharacterTransformer}
     * {@link Integer}      对应于 {@link com.superywd.aion.commons.properties.transformers.IntegerTransformer}
     * {@link Short}        对应于 {@link com.superywd.aion.commons.properties.transformers.ShortTransformer}
     * @return  键的具体类型
     */
    Class<? extends PropertyTransformer> propertyTransformer() default PropertyTransformer.class;
}
