package com.jared.core.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by yangjunde on 15/3/31.
 */
public class Preloader {
    private final FutureTask<String> future =  new FutureTask<String>(new Callable<String>() {
        @Override
        public String call() throws Exception {
            Thread.sleep(5000);
            return "helloWorld";
        }
    });

    private final Thread thread = new Thread(future);

    public void start(){
        thread.start();
    }

    public String get(){
        String res = "";
        try {
            res = future.get();
            System.out.println(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args){
        Preloader preloader = new Preloader();
        preloader.start();
        preloader.get();
        System.out.println("end....");
    }
}
