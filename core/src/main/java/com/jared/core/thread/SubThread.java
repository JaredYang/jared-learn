package com.jared.core.thread;

/**
 * Created by yangjunde on 15/4/16.
 */
public class SubThread extends Thread{
    @Override
    public void run() {
        work();
        System.out.println("sub thread stop");
    }

    private void work(){
        try {
            System.out.println("sub thread start");
            sleep(5000L);
            System.out.println("sub thread sleep end");
        } catch (InterruptedException e) {
            System.out.println("sub thread interrupt");
        }
    }

}