package com.jared.core.thread;

/**
 * Created by yangjunde on 2016/11/16.
 */
public class TestWait {

    private Object lock = new Object();
    private boolean condition;

    class WaitThread extends Thread{

        public WaitThread(String name){
            super(name);
        }

        public void run(){
            synchronized (lock){
                try {
                    while (!condition){
                        System.out.println( getName() + " begin wait");
                        long b = System.currentTimeMillis();
                        lock.wait(5000);
                        System.out.println(getName() + "wait " + (System.currentTimeMillis() - b ) + "ms");
                    }
                    System.out.println(getName() + "done !");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    class NotifyThread extends Thread{

        public NotifyThread(String name){
            super(name);
        }

        public void run(){
            try {
                synchronized (lock){
                    sleep(1000);
                    condition = true;
                    lock.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        TestWait testWait = new TestWait();
        WaitThread waitThread = testWait.new WaitThread("wait1");
        NotifyThread notifyThread = testWait.new NotifyThread("notify1");
        waitThread.start();
        notifyThread.start();

    }


}
