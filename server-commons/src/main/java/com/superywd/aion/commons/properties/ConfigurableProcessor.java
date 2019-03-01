package com.superywd.aion.commons.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Properties;

/**
 * @author: 迷宫的中心
 * @date: 2019/1/24 01:53
 */

public class ConfigurableProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurableProcessor.class);

    /**
     * 向指定的配置类的静态成员变量中注入配置值
     * @param object 指定的配置类，可以是类描述对象，也可以是一个对象
     * @param properties 需要被注入的值
     */
    public static void process(Object object, List<Properties> properties){
        Class<?> clazz;
        // 类描述对象
        if(object instanceof Class){
            clazz = (Class<?>) object;
            object = null;
        }else{
            clazz = object.getClass();
        }
        process(clazz,object,properties);
    }

    private static void process(Class<?> clazz,Object object, List<Properties> properties){
        processFields(clazz, object, properties);
    }

    private static void processFields(Class<?> clazz,Object object, List<Properties> properties){
        for(Field f : clazz.getDeclaredFields()){
            // 跳过对象的静态变量，不做注入
            if(Modifier.isStatic(f.getModifiers()) && object != null){
                continue;
            }
            // 跳过类的非静态变量，不做注入
            if(!Modifier.isStatic(f.getModifiers()) && object == null){
                continue;
            }
            if(f.isAnnotationPresent(Property.class)){
                // final修饰的变量无法被注入
                if(Modifier.isFinal(f.getModifiers())){
                    logger.error("尝试对被final修饰的成员变量 {} 进行注入，在类 {} 中",f.getName(),clazz.getName());
                    throw new RuntimeException();
                }
            }
        }
    }

    private static void processFields(Field field,Object object, List<Properties> properties){
        boolean oldAccessible = field.isAccessible();
        //二话不多说，强制拿到修改权
        field.setAccessible(true);
        try{
            Property property = field.getAnnotation(Property.class);
            //如果注解中的默认值属性没有被指定或者注解中的键名在配置中存在，就注入这个属性
            if(Property.DEFAULT_VALUE.equals(property.defaultValue()) || keyExist(property.key(),properties)){
                field.set(object,getFieldValue(field,properties));
            } else{
                logger.debug("类 {} 中的属性 {} 因配置不支持而没有被注入...",
                        field.getDeclaringClass().getName(),field.getName());
            }
        }catch (Exception e){
            logger.error("类 {} 中的属性 {} 注入发生异常！");
            throw new RuntimeException();
        }
        //注入完了改回去
        field.setAccessible(oldAccessible);
    }

    private static Object getFieldValue(Field field,List<Properties> properties) throws InstantiationException, IllegalAccessException {
        Property property = field.getAnnotation(Property.class);
        String defaultValue = property.defaultValue();
        String key = property.key();
        String value = null;
        if(key.isEmpty()){
            logger.warn("类 {} 的待注入成员变量 {} 的key值为null！",
                    field.getDeclaringClass().getName(),field.getName());
        }else{
            value = findPropertyByKey(key,properties);
        }
        // 如果key值是空的，或者value值没有取到，则最后会被当成defaultValue来处理
        if(value == null || "".equals(value.trim())){
            value = defaultValue;
            logger.debug("类 {} 的带注入成员变量 {} 被注入了默认值",
                    field.getDeclaringClass().getName(),field.getName());
        }
        PropertyTransformer<?> transformer = PropertyTransformerFactory.newTransformer(
                field.getType(),property.propertyTransformer());
        return transformer.transform(value,field);
    }

    /**
     * 从配置类集合中查找指定的配置的值
     * @param key           指定的配置键名
     * @param properties    配置类集合
     * @return              配置值
     */
    private static String findPropertyByKey(String key,List<Properties> properties){
        for(Properties item: properties){
            if(item.containsKey(key)){
                return item.getProperty(key);
            }
        }
        return null;
    }

    /**
     * 判断键名是否在配置中存在
     * @param key           指定的配置键名
     * @param properties    配置集合
     * @return              是否存在
     */
    private static boolean keyExist(String key,List<Properties> properties){
        return findPropertyByKey(key,properties) != null;
    }
}
