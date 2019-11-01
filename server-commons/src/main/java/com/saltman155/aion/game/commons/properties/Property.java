package com.saltman155.aion.game.commons.properties;

import com.saltman155.aion.game.commons.properties.transformers.*;

import java.lang.annotation.*;

/**
 * @author: saltman155
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
    String defaultValue() default DEFAULT_VALUE;

    /**
     * 键的具体类型
     * {@link Boolean }     对应于 {@link BooleanTransformer}
     * {@link Byte}         对应于 {@link ByteTransformer}
     * {@link Character}    对应于 {@link CharacterTransformer}
     * {@link Integer}      对应于 {@link IntegerTransformer}
     * {@link Short}        对应于 {@link ShortTransformer}
     * @return  键的具体类型
     */
    Class<? extends PropertyTransformer> propertyTransformer() default PropertyTransformer.class;
}
