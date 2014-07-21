package com.jared.core.concurrent;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 14-7-18
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public class CallableDemo {

    static class TaskWithResult implements Callable<String> {
        private int id;
        public TaskWithResult(int id){
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            return "result id : " + id;
        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newCachedThreadPool();
        ArrayList<Future<String>> futures = new ArrayList<Future<String>>();
        try {
            for (int i=0; i<10 ; i++){
                futures.add(executor.submit(new TaskWithResult(i)));
            }
            for (Future<String> future : futures){
                System.out.println(future.get());

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }

}
