package com.saltman155.aion.game.commons.callbacks;

/**
 *  用于在非默认值时标记回调优先级的接口
 *               如果优先级是默认值，则回调不必实现此接口
 *               具有更高优先级的侦听器会更早执行。
 * @author: saltman155
 * @date: 2018/10/28 15:51
 */

public interface CallbackPriority {

    /**
     * 默认的回调方法优先级
     */
    int DEFAULT_PRIORITY = 0;

    /**
     * 获取优先级
     * @return 优先级
     */
    int getPriority();
}
