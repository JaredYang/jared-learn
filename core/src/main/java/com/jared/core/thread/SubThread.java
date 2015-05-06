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
            sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}