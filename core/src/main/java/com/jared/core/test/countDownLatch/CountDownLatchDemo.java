package com.jared.core.test.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-22
 * Time: 下午3:46
 * To change this template use File | Settings | File Templates.
 */
public class CountDownLatchDemo {
    private static final int PLAYER_AMOUNT = 5;

    public CountDownLatchDemo() {

    }

    public static void main(String[] args) {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(PLAYER_AMOUNT);
        ExecutorService exe = Executors.newFixedThreadPool(PLAYER_AMOUNT);
        for (int i = 0; i < PLAYER_AMOUNT; i++) {
            Player player = new Player(i + 1, begin, end);
            exe.submit(player);
        }
        begin.countDown();
        System.out.println("game begin!");
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("game over!");
        exe.shutdown();
    }
}
