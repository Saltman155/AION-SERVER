package com.superywd.aion.login.model;

import java.sql.Timestamp;

/**
 * @author: saltman155
 * @date: 2019/3/16 00:18
 */

public class AccountTime {

    /**最后登录时间*/
    private Timestamp lastLoginTime;
    /**账户剩余可用时间*/
    private Timestamp expirationTime;
    /**账户惩罚剩余时间*/
    private Timestamp penaltyEnd;
    /**累计会话（本次）时间*/
    private Long sessionDuration;
    /**累计在线时间*/
    private Long accumulatedOnlineTime;
    /**累计休息时间*/
    private Long accumulatedRestTime;

    public AccountTime() {
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Timestamp getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Timestamp expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Timestamp getPenaltyEnd() {
        return penaltyEnd;
    }

    public void setPenaltyEnd(Timestamp penaltyEnd) {
        this.penaltyEnd = penaltyEnd;
    }

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

    @Override
    public String toString() {
        return "AccountTime{" +
                "lastLoginTime=" + lastLoginTime +
                ", expirationTime=" + expirationTime +
                ", penaltyEnd=" + penaltyEnd +
                ", sessionDuration=" + sessionDuration +
                ", accumulatedOnlineTime=" + accumulatedOnlineTime +
                ", accumulatedRestTime=" + accumulatedRestTime +
                '}';
    }
}
