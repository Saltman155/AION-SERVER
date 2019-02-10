package com.superywd.aion.commons.callbacks;

/**
 *  基本的回调类，每个增强的方法都会调用它的 “beforeCall”和“afterCall”方法
 * 这个和spring里面的aop非常像，但是它这里是用更底层的方式来实现的。
 * @author: 迷宫的中心
 * @date: 2018/10/21 18:56
 */

public interface Callback<T> {


    /**
     * 在调用实际方法之前调用的方法。
     * 这个回调方法的执行应返回以下结果之一：
     * @param obj       调用的目标
     * @param args      方法参数
     * @return
     */
    CallbackResult beforeCall(T obj, Object[] args);


    /**
     * 在调用实际方法之后调用这个方法。
     * @param obj               调用的目标
     * @param args              方法参数
     * @param methodResult      方法的返回结果
     * @return
     */
    CallbackResult afterCall(T obj, Object[] args, Object methodResult);


    /**
     * 返回这个接口的实际实现类
     * @return
     */
    Class<? extends Callback> getBaseClass();

}
