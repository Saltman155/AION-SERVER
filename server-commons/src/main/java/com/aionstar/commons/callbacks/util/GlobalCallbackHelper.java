package com.aionstar.commons.callbacks.util;

import com.aionstar.commons.callbacks.Callback;
import com.aionstar.commons.callbacks.CallbackResult;
import com.aionstar.commons.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author: saltman155
 * @date: 2018/11/3 20:35
 */

public class GlobalCallbackHelper {

    private static final Logger logger = LoggerFactory.getLogger(GlobalCallbackHelper.class);


    private static final CopyOnWriteArrayList<Callback> GLOBAL_CALLBACKS = new CopyOnWriteArrayList<Callback>();

    /**
     * 私有化构造器
     */
    private GlobalCallbackHelper() {
    }

    /**
     *
     * @param callback
     */
    public static void addCallback(Callback<?> callback){
        synchronized (GlobalCallbackHelper.class){
            CallbackUtil.insertCallbackToList(callback, GLOBAL_CALLBACKS);
        }
    }

    public static void removeCallback(Callback<?> callback) {
        synchronized (GlobalCallbackHelper.class){
            GLOBAL_CALLBACKS.remove(callback);
        }
    }


    /**
     * 前置执行的代理方法
     * @param obj                       被代理的方法所处的类
     * @param callbackClass             代理方法类
     * @param args                      被代理的方法的实参
     * @return                          返回的结果
     */
    @SuppressWarnings({"unchecked"})
    public static CallbackResult<?> beforeCall(Object obj, Class callbackClass, Object... args) {

        CallbackResult<?> cr = null;
        for (Callback cb : GLOBAL_CALLBACKS) {
            //查询所有的代理方法类，不是当前的代理类就跳过它
            if (!ClassUtil.isSubclass(cb.getBaseClass(), callbackClass)) {
                continue;
            }
            try {
                cr = cb.beforeCall(obj, args);
                if (cr.isBlockingCallbacks()) {
                    break;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return cr == null ? CallbackResult.newContinue() : cr;
    }


    /**
     * 后置执行的代理方法
     * @param obj                         被代理的方法所处的类
     * @param callbackClass               代理方法类
     * @param args                        被代理的方法的实参列表
     * @param result                      返回结果
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public static CallbackResult<?> afterCall(Object obj,Class callbackClass, Object[] args, Object result) {
        CallbackResult<?> cr = null;
        for (Callback cb : GLOBAL_CALLBACKS) {
            if (!ClassUtil.isSubclass(cb.getBaseClass(), callbackClass)) {
                continue;
            }
            try {
                cr = cb.afterCall(obj, args, result);
                if (cr.isBlockingCallbacks()) {
                    break;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return cr == null ? CallbackResult.newContinue() : cr;
    }




}
