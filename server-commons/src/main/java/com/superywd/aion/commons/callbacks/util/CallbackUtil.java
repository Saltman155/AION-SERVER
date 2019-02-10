package com.superywd.aion.commons.callbacks.util;

import com.superywd.aion.commons.callbacks.Callback;
import com.superywd.aion.commons.callbacks.CallbackPriority;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 *
 * @author: 迷宫的中心
 * @date: 2018/10/22 23:14
 */

public class CallbackUtil {

    /**
     * 判断指定的方法是否携带增强注解
     * @param method
     * @param annotation
     * @return
     */
    public static boolean isAnnotationPresent(CtMethod method, Class<? extends Annotation> annotation){
        for(Object o:method.getMethodInfo().getAttributes()){
            if(o instanceof AnnotationsAttribute){
                AnnotationsAttribute attribute=(AnnotationsAttribute) o;
                if(attribute.getAnnotation(annotation.getName()) != null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取代理方法类的权重（默认权重为0）
     * @param callback
     * @return
     */
    public static int getCallbackPriority(Callback callback) {
        if (callback instanceof CallbackPriority) {
            CallbackPriority instancePriority = (CallbackPriority) callback;
            return CallbackPriority.DEFAULT_PRIORITY - instancePriority.getPriority();
        } else {
            return CallbackPriority.DEFAULT_PRIORITY;
        }
    }


    protected static void insertCallbackToList(Callback callback, List<Callback> list) {

        int callbackPriority = CallbackUtil.getCallbackPriority(callback);
        if (!list.isEmpty()) {
            // 应保证这个代理类容器的有序性，这个容器遵从（默认权重-权重）从小到大排序
            for (int i = 0, n = list.size(); i < n; i++) {
                Callback c = list.get(i);
                int nowPriority = CallbackUtil.getCallbackPriority(c);
                //如果当前的代理类权重小于即将插入的代理类权重，就将待插入的代理类插入当前位置
                if (callbackPriority < nowPriority) {
                    list.add(i, callback);
                    return;
                }
            }
        }
        //如果都比当前大，就插入尾部
        list.add(callback);
    }

}
