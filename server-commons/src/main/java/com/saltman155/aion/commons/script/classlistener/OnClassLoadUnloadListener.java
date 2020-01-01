package com.saltman155.aion.commons.script.classlistener;

import com.saltman155.aion.commons.script.metadata.OnClassLoad;
import com.saltman155.aion.commons.script.metadata.OnClassUnLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 处理脚本中，用注解
 *       {@link OnClassLoad } 及
 *       {@link OnClassUnLoad }
 *       修饰了的方法的调用
 */
public class OnClassLoadUnloadListener implements ClassListener {

    private static final Logger logger = LoggerFactory.getLogger(OnClassLoadUnloadListener.class);

    @Override
    public void postLoad(Class<?>[] classes) {
        for(Class<?> clazz :classes){
            for (Method method : clazz.getDeclaredMethods()) {
                if (!Modifier.isStatic(method.getModifiers())) { continue; }
                boolean accessible = method.isAccessible();
                method.setAccessible(true);
                if (method.getAnnotation(OnClassLoad.class) != null) {
                    try {
                        method.invoke(null);
                    }
                    catch (Exception e) {
                        logger.error("调用脚本类 {} 的加载事件方法 {} 失败！",clazz.getName(),method.getName());
                    }
                }
                method.setAccessible(accessible);
            }
        }
    }

    @Override
    public void preUnload(Class<?>[] classes) {
        for(Class<?> clazz :classes){
            for (Method method : clazz.getDeclaredMethods()) {
                if (!Modifier.isStatic(method.getModifiers())) { continue; }
                boolean accessible = method.isAccessible();
                method.setAccessible(true);
                if (method.getAnnotation(OnClassUnLoad.class) != null) {
                    try {
                        method.invoke(null);
                    }
                    catch (Exception e) {
                        logger.error("调用脚本类 {} 的卸载事件方法 {} 失败！",clazz.getName(),method.getName());
                    }
                }
                method.setAccessible(accessible);
            }
        }
    }


}
