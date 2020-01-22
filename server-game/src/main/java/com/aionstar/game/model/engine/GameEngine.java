package com.aionstar.game.model.engine;

import java.util.concurrent.CountDownLatch;

/**
 * 游戏各模块引擎的基类
 * @author: saltman155
 * @date: 2019/5/1 20:30
 */
public interface GameEngine {

    /**
     * 启动引擎
     * @param processLatch 同步锁，启动后释放锁
     */
    void load(CountDownLatch processLatch);

    /**
     * 清理&保存资源并关闭引擎
     */
    void shutdown();
}
