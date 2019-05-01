package com.superywd.aion.commons.callbacks;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *     所有增强对象的通用接口。
 * @author: saltman155
 * @date: 2018/10/22 23:26
 */

public interface EnhancedObject {


    /**
     * 添加对此对象的回调
     * @param callback
     */
    public void addCallback(Callback callback);


    /**
     * 删除对此对象的回调
     * @param callback
     */
    public void removeCallback(Callback callback);


    /**
     * 返回对此对象相关的所有回调
     * 对此映射的迭代不是线程安全的，请确保{@link #getCallbackLock()}已被锁定
     *
     * @return 包含与此对象关联的回调，如果没有回调则返回null
     */
    public Map<Class<? extends Callback>, List<Callback>> getCallbacks();


    /**
     * 将回调映射与此对象关联。
     * 调用此方法时，请确保{@link #getCallbackLock()}处于写入模式锁定状态
     *
     * @param callbacks
     */
    public void setCallbacks(Map<Class<? extends Callback>, List<Callback>> callbacks);


    /**
     * 返回用于确保线程安全的锁
     * @return
     */
    public ReentrantReadWriteLock getCallbackLock();
}
