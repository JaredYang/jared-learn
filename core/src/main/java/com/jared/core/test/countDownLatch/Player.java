package com.jared.core.test.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-22
 * Time: 下午3:37
 * To change this template use File | Settings | File Templates.
 */
public class Player implements Runnable {
    private int id;
    private CountDownLatch begin;
    private CountDownLatch end;

    public Player(int id, CountDownLatch begin, CountDownLatch end) {
        super();
        this.id = id;
        this.begin = begin;
        this.end = end;
    }


    @Override
    public void run() {
        try {
            begin.await();
            Thread.sleep((long) (Math.random()*1000));
            System.out.println("No" + id  + "arrived!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            end.countDown();
        }
    }
}
