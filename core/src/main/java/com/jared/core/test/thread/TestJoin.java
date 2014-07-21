package com.jared.core.test.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 14-7-18
 * Time: 下午6:29
 * To change this template use File | Settings | File Templates.
 */
public class TestJoin extends Thread {

    private int id;

    public TestJoin(int id){
        this.id = id;
    }

    @Override
    public void run() {
       for (int i=0;i<1000;i++){
           System.out.println(">>>>>>" + 1);
       }
    }

    public static void main(String[] args)throws Exception{
        TestJoin testJoin = new TestJoin(1);
        testJoin.start();
        testJoin.join();
        System.out.println(">>>>>>>>> end");

    }
}
