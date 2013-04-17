package com.jared.core.test.thread;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-7
 * Time: 下午6:29
 * To change this template use File | Settings | File Templates.
 */
public class ThreadTest implements Runnable {
    @Override
    public void run() {
        int i = 0;
        while (i < 10){
            System.out.println("thread run " + Thread.currentThread().getName());
            i ++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadTest threadTest1 = new ThreadTest();
        Thread thread1 = new Thread(threadTest1);
        thread1.setName("Thread1");
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread1.start();

        ThreadTest threadTest2 = new ThreadTest();
        Thread thread2 = new Thread(threadTest2);
        thread2.setName("Thread2");
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread2.start();
    }
}
