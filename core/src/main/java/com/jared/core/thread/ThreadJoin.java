package com.jared.core.thread;

/**
 * Created by yangjunde on 15/4/16.
 */
public class ThreadJoin {

    public static void main(String[] args) throws Exception{
        SubThread subThread = new SubThread();
        subThread.start();
        mainWork();
        //subThread.interrupt();
        subThread.join();
        System.out.println("main thread stop");
    }

    private static void mainWork(){
        try {
            System.out.println("main thread start");
            Thread.sleep(3000L);
            System.out.println("main thread sleep end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
