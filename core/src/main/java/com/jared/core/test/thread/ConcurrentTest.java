package com.jared.core.test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-7
 * Time: 下午5:56
 * To change this template use File | Settings | File Templates.
 */
public class ConcurrentTest {

    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i<10){
                    i++;
                    System.out.println("Thread 1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        executor.submit(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 10) {
                    i++;
                    System.out.println("Thread 2");
                }
            }
        });

    }
}
