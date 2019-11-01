package com.saltman155.aion.login.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: saltman155
 * @date: 2019/3/17 21:39
 */

public class TaskExecutionWebServer {

    private static final int NUM = 100;

    private static final ExecutorService exec = Executors.newFixedThreadPool(NUM);

    public static void main(String[] args) {
        exec.execute(()-> System.out.println("haha"));
//        exec.shutdownNow();
        exec.shutdown();
    }
}
