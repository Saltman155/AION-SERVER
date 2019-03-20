package com.superywd.aion.login.utils;


import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManagerTest {


    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        for(int i = 0;i<3;i++){
            Task worker = new Task("Task-" + i);
            scheduledExecutorService.scheduleAtFixedRate(worker,0,5, TimeUnit.SECONDS);
        }

        Thread.sleep(100000);

        System.out.println("准备关闭线程池...");
        scheduledExecutorService.shutdown();
        boolean isDone;
        do {
            isDone = scheduledExecutorService.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("awaitTermination...");
        }while(!isDone);
        System.out.println("结束所有线程");
    }

}

class Task implements Runnable {

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("name = " + name + ", startTime = " + new Date());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("name = " + name + ", endTime = " + new Date());
    }

}