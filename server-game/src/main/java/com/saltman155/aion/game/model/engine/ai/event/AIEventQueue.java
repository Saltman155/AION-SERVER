package com.saltman155.aion.game.model.engine.ai.event;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * AI事件维护队列
 * @author: saltman155
 * @date: 2019/5/6 19:56
 */
public class AIEventQueue extends LinkedBlockingDeque<AIEventType> {

    private static final long serialVersionUID = -7234174243343636729L;

    public AIEventQueue() {
        super();
    }

    /**
     * 使用指定的大小初始化容器
     * @param capacity  指定大小
     */
    public AIEventQueue(int capacity) {
        super(capacity);
    }

    /**
     * 重写插入头部方法
     * @param e         待插入事件
     * @return          插入是否成功
     */
    @Override
    public synchronized boolean offerFirst(AIEventType e) {
        //如果已经满了，就移除最后一个事件
        if (remainingCapacity() == 0) {
            removeLast();
        }
        super.offerFirst(e);
        return true;
    }
}
