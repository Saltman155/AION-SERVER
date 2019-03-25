package com.superywd.aion.commons.service.cron;

/**
 * 自定义的定时任务调度异常类
 * @author: 迷宫的中心
 * @date: 2018/11/4 13:50
 */

public class CronServiceException extends RuntimeException {


   private static final long serialVersionUID = -354186843536711803L;
 public CronServiceException() {
    }

    public CronServiceException(String message) {
        super(message);
    }

    public CronServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CronServiceException(Throwable cause) {
        super(cause);
    }
}
