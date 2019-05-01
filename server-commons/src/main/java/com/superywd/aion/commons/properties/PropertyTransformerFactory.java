package com.superywd.aion.commons.properties;

import com.superywd.aion.commons.properties.transformers.*;

/**
 *
 * 获取实例对象工厂类
 * @author: saltman155
 * @date: 2019/3/1 22:33
 */

public class PropertyTransformerFactory {

    /**
     * 获取类型类的实例对象
     * @param clazzToTransform      待注入的变量类型
     * @param tc                    待注入的变量上声明的类型类
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static PropertyTransformer newTransformer(Class clazzToTransform,Class<? extends PropertyTransformer> tc)
            throws IllegalAccessException, InstantiationException {
        // 当传入的类型类不是基类（则是它的子类），则直接调用newInstance方法获取实例
        if(tc != PropertyTransformer.class){
            return tc.newInstance();
        }
        if(clazzToTransform == String.class){
            return StringTransformer.INSTANCE;
        }
        // 否则只能根据 clazz来判断了
        if(clazzToTransform == Boolean.class || clazzToTransform == Boolean.TYPE){
            return BooleanTransformer.INSTANCE;
        }
        if(clazzToTransform == Byte.class || clazzToTransform == Byte.TYPE){
            return ByteTransformer.INSTANCE;
        }
        if(clazzToTransform == Character.class || clazzToTransform == Character.TYPE){
            return CharacterTransformer.INSTANCE;
        }
        if(clazzToTransform == Integer.class || clazzToTransform == Integer.TYPE){
            return IntegerTransformer.INSTANCE;
        }
        if(clazzToTransform == Short.class || clazzToTransform == Short.TYPE){
            return ShortTransformer.INSTANCE;
        }
        throw new RuntimeException("无法在预置的类型中无法找到对应的类型");
    }
}
