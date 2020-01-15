package com.saltman155.aion.game.utils;

import com.aionstar.commons.callbacks.Callback;
import com.aionstar.commons.callbacks.CallbackResult;
import com.aionstar.commons.callbacks.EnhancedObject;
import com.aionstar.commons.callbacks.metadata.GlobalCallback;
import com.aionstar.commons.callbacks.metadata.ObjectCallback;
import com.aionstar.commons.callbacks.util.GlobalCallbackHelper;

/**
 * javaAgent检查工具类
 * @author: saltman155
 * @date: 2019/5/4 23:19
 */
public class JavaAgentUtil {

    static{
        GlobalCallbackHelper.addCallback(new CheckCallback());
    }

    /**
     * 检查JavaAgent有没有设置
     * @return  是否设置了JavaAgent
     */
    public static boolean checkConfigured(){
        JavaAgentUtil jau =  new JavaAgentUtil();
        //未代理前类一定不是EnhancedObject类型，但代理后新类会继承该类型
        if(!(jau instanceof EnhancedObject)){
            throw new Error("JavaAgent未配置！请配置 -javaagent 以确保方法回调能正常执行！");
        }
        //未代理前方法必定返回false，从而产生异常，但加入Global前置代理后会变为提前返回true
        if(!checkGlobalCallback()){
            throw new Error("Global callbacks 失效！");
        }
        //向该类的成员变量——代理对象表Map<CheckCallback,List<CheckCallback>> 中放入检查用的代理方法类
        ((EnhancedObject)jau).addCallback(new CheckCallback());
        //检查该类的类代理是否生效
        if(!jau.checkObjectCallback()) {
            throw new Error("Object callbacks 失效！");
        }
        return true;
    }

    @GlobalCallback(CheckCallback.class)
    private static boolean checkGlobalCallback() {
        return false;
    }

    @ObjectCallback(CheckCallback.class)
    private boolean checkObjectCallback() {
        return false;
    }

    public static class CheckCallback implements Callback {

        @Override
        public CallbackResult beforeCall(Object obj, Object[] args) {
            return CallbackResult.newFullBlocker(true);
        }

        @Override
        public CallbackResult afterCall(Object obj, Object[] args, Object methodResult) {
            return CallbackResult.newContinue();
        }

        @Override
        public Class<? extends Callback> getBaseClass() {
            return CheckCallback.class;
        }
    }
}
