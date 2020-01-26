package com.aionstar.login.model.entity;

import java.time.LocalDateTime;

/**
 * @author: saltman155
 * @date: 2019/3/16 00:18
 */

public class AccountTime {

    /**最后活跃时间*/
    private LocalDateTime lastActiveTime;
    /**账户惩罚剩余时间*/
    private LocalDateTime penaltyEnd;
    /**账户剩余可用时间*/
    private Long expirationTime;
    /**累计会话（本次）时间*/
    private Long sessionDuration;
    /**累计在线时间*/
    private Long accumulatedOnlineTime;
    /**累计休息时间*/
    private Long accumulatedRestTime;

    public AccountTime() {
    }

    public LocalDateTime getLastActiveTime() { return lastActiveTime; }

    public void setLastActiveTime(LocalDateTime lastActiveTime) { this.lastActiveTime = lastActiveTime; }

    public Long getExpirationTime() { return expirationTime; }

    public void setExpirationTime(Long expirationTime) { this.expirationTime = expirationTime; }

    public LocalDateTime getPenaltyEnd() { return penaltyEnd; }

    public void setPenaltyEnd(LocalDateTime penaltyEnd) { this.penaltyEnd = penaltyEnd; }

    public Long getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Long sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public Long getAccumulatedOnlineTime() {
        return accumulatedOnlineTime;
    }

    public void setAccumulatedOnlineTime(Long accumulatedOnlineTime) {
        this.accumulatedOnlineTime = accumulatedOnlineTime;
    }

    public Long getAccumulatedRestTime() {
        return accumulatedRestTime;
    }

    public void setAccumulatedRestTime(Long accumulatedRestTime) {
        this.accumulatedRestTime = accumulatedRestTime;
    }

}
