package com.superywd.aion.commons.script.classlistener;

/**
 * 类装载事件监听器顶层接口
 * @author: saltman155
 * @date: 2019/4/18 13:55
 */
public interface ClassListener {

    /**
     * 在类被装载后调用此方法
     * @param classes   已经被装载的类
     */
    void postLoad(Class<?>[] classes);

    /**
     * 在类被卸载前调用此方法
     * @param classes   即将被卸载的类
     */
    void preUnload(Class<?>[] classes);
}
