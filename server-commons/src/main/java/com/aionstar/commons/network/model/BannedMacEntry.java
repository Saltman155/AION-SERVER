package com.aionstar.commons.network.model;

import java.time.LocalDateTime;

/**
 * MAC封禁的一些数据
 * @author saltman155
 * @date 2020/1/18 21:39
 */

public class BannedMacEntry {

    private String mac;
    private String details;
    private LocalDateTime timeEnd;

    public final void setDetails(String details) {
        this.details = details;
    }

    public final String getDetails() {
        return details;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public final boolean isActive() {
        return timeEnd != null && timeEnd.isAfter(LocalDateTime.now());
    }

    public final boolean isActiveTill(LocalDateTime time) {
        return timeEnd != null && timeEnd.isAfter(time);
    }

}