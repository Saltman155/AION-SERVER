package com.superywd.aion.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 包含类的一些常用操作
 * @author: 迷宫的中心
 * @date: 2018/11/3 21:54
 */

public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 判断 类a是否等于类b或是类b的子类
     * @param a 类a
     * @param b 类b
     * @return true  当 a == b 或 a继承b 或 a实现了b
     */
    public static boolean isSubclass(Class<?> a,Class<?> b){
        if(a == b){
            return true;
        }
        if(a == null || b == null){
            return false;
        }
        // TODO: 从本身一直向上查找，感觉这里是不是可以优化
        for(Class<?> x = a; x != null; x=x.getSuperclass()){
            if (x == b){
                return true;
            }
            //如果类b是个接口，就查询它实现的所有接口一一判断
            if( b.isInterface()){
                Class<?>[] interfaces = x.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    if (isSubclass(anInterface, b)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Set<String> getClassNameFromPackage(){

    }
}
