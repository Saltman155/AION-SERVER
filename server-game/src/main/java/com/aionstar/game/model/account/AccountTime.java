package com.aionstar.game.model.account;

/**
 * 该类表示账号的各类游戏时间信息
 */

public class AccountTime {

    private long accumulatedOnlineTime;

    private long accumulatedRestTime;

    public AccountTime(long accumulatedOnlineTime, long accumulatedRestTime) {
        this.accumulatedOnlineTime = accumulatedOnlineTime;
        this.accumulatedRestTime = accumulatedRestTime;
    }

    public long getAccumulatedOnlineTime() { return accumulatedOnlineTime; }

    public void setAccumulatedOnlineTime(long accumulatedOnlineTime) { this.accumulatedOnlineTime = accumulatedOnlineTime; }

    public long getAccumulatedRestTime() { return accumulatedRestTime; }

    public void setAccumulatedRestTime(long accumulatedRestTime) { this.accumulatedRestTime = accumulatedRestTime; }

}
