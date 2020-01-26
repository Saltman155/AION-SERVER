package com.aionstar.commons.callbacks.util;

import com.aionstar.commons.callbacks.Callback;
import com.aionstar.commons.callbacks.CallbackResult;
import com.aionstar.commons.callbacks.EnhancedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *  实现回调辅助方法的类,所有增强对象都将其逻辑的主要部分委托给此类
 *
 * @author: saltman155
 * @date: 2018/10/26 22:38
 */

public class ObjectCallbackHelper {

    private static final Logger logger = LoggerFactory.getLogger(ObjectCallbackHelper.class);

    private ObjectCallbackHelper() {
    }


    /**
     * 添加代理方法类到代理类的代理队列中
     * @param callback
     * @param object
     */
    public static void addCallback(Callback callback, EnhancedObject object){
        try {
            object.getCallbackLock().writeLock().lock();
            Map<Class<? extends Callback>, List<Callback>> cbMap = object.getCallbacks();
            if (cbMap == null) {
                //原版用的这个，我换一下试试
                //cbMap = Maps.newHashMap();
                cbMap = new ConcurrentHashMap<>();
                object.setCallbacks(cbMap);
            }
            List<Callback> list = cbMap.get(callback.getBaseClass());
            if (list == null) {
                //这个容器是读写分离的，有空研究一下
                list = new CopyOnWriteArrayList<>();
                cbMap.put(callback.getBaseClass(), list);
            }

            CallbackUtil.insertCallbackToList(callback, list);
        } finally {
            object.getCallbackLock().writeLock().unlock();
        }
    }


    /**
     * 从指定的代理类的代理队列中移除指定的代理方法类
     * @param callback
     * @param object
     */
    public static void removeCallback(Callback callback, EnhancedObject object) {
        try {
            object.getCallbackLock().writeLock().lock();
            Map<Class<? extends Callback>, List<Callback>> cbMap = object.getCallbacks();
            if (cbMap == null || cbMap.isEmpty()) {
                return;
            }
            List<Callback> list = cbMap.get(callback.getBaseClass());
            if (list == null || !list.remove(callback)) {
                // 如果代理方法类对应的代理队列不存在，或者从代理队列中移除失败了，就抛出异常
                logger.error("尝试移除代理方法类:" + callback.getClass().getSimpleName() + "但失败了", new RuntimeException());
                return;
            }
            //如果移除成功后，代理队列为空，就将队列一并移除
            if (list.isEmpty()) {
                cbMap.remove(callback.getBaseClass());
            }
            //如果移除成功后，代理队列的映射集合为空，就置空映射集合
            if (cbMap.isEmpty()) {
                object.setCallbacks(null);
            }
        } finally {
            object.getCallbackLock().writeLock().unlock();
        }
    }

    /**
     * 此方法在实际方法执行发生之前运行
     *
     * @param obj                   实际方法的所属对象
     * @param callbackClass
     * @param args
     * @return
     */
    public static CallbackResult<?> beforeCall(EnhancedObject obj, Class callbackClass, Object... args){
        Map<Class<? extends Callback>, List<Callback>> cbMap = obj.getCallbacks();
        if (cbMap == null || cbMap.isEmpty()) {
            return CallbackResult.newContinue();
        }
        List<Callback> list = null;
        //获取线程安全的包含代理方法的类
        try {
            obj.getCallbackLock().readLock().lock();
            list = cbMap.get(callbackClass);
        } finally {
            obj.getCallbackLock().readLock().unlock();
        }
        if(list == null || list.isEmpty()){
            return CallbackResult.newContinue();
        }
        CallbackResult<?> cbr = null;
        //执行所有的回调链
        for(Callback c :list){
            try {
                cbr = c.beforeCall(obj, args);
                if (cbr.isBlockingCallbacks()) {
                    break;
                }
            } catch (Exception e) {
                logger.error("前置代理方法中发生异常", e);
            }
        }
        //返回执行结果
        return cbr == null ? CallbackResult.newContinue() : cbr;
    }


    /**
     *
     * @param obj
     * @param callbackClass
     * @param args
     * @param result
     * @return
     */
    public static CallbackResult<?> afterCall(EnhancedObject obj, Class callbackClass, Object[] args, Object result) {
        Map<Class<? extends Callback>, List<Callback>> cbMap = obj.getCallbacks();
        if (cbMap == null || cbMap.isEmpty()) {
            return CallbackResult.newContinue();
        }
        CallbackResult<?> cr = null;
        List<Callback> list = null;
        try {
            obj.getCallbackLock().readLock().lock();
            list = cbMap.get(callbackClass);
        } finally {
            obj.getCallbackLock().readLock().unlock();
        }
        if (list == null || list.isEmpty()) {
            return CallbackResult.newContinue();
        }
        for (Callback c : list) {
            try {
                cr = c.afterCall(obj, args, result);
                if (cr.isBlockingCallbacks()) {
                    break;
                }
            } catch (Exception e) {
                logger.error("后置代理方法中发生异常", e);
            }
        }
        return cr == null ? CallbackResult.newContinue() : cr;
    }
}
