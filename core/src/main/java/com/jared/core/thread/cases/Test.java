package com.jared.core.thread.cases;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangjunde on 2017/9/25.
 */
public class Test {
    public static void main(String[] args) {
        MajusculeABC maj = new MajusculeABC();
        Thread t_a = new Thread(new Thread_ABC(maj,"A"));
        Thread t_b = new Thread(new Thread_ABC(maj,"B"));
        Thread t_c = new Thread(new Thread_ABC(maj,"C"));
        t_a.start();
        t_b.start();
        t_c.start();
    }



}

class MajusculeABC{
    public AtomicInteger total = new AtomicInteger();
    public void printMajuscule(String word){
        System.out.print(word);
        total.incrementAndGet();
    }
}

class Thread_ABC implements Runnable{
    private final MajusculeABC majusculeABC;

    private String name;

    public Thread_ABC(final MajusculeABC majusculeABC , String name){
        this.majusculeABC = majusculeABC;
        this.name = name;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0){
            //System.out.println("thread>" + name + " count >" + count);
            //System.out.println("total" + majusculeABC.total);
            synchronized (majusculeABC){
                if (name.equals("A")){
                    if (majusculeABC.total.get() % 3 != 0){
                        try {
                            majusculeABC.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        majusculeABC.printMajuscule(name);
                        count --;
                        majusculeABC.notifyAll();
                    }
                }
                if (name.equals("B")){
                    if (majusculeABC.total.get() % 3 != 1){
                        try {
                            majusculeABC.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        majusculeABC.printMajuscule(name);
                        count --;
                        majusculeABC.notifyAll();
                    }
                }
                if (name.equals("C")){
                    if (majusculeABC.total.get() % 3 != 2){
                        try {
                            majusculeABC.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        majusculeABC.printMajuscule(name);
                        count --;
                        majusculeABC.notifyAll();
                    }
                }
            }
        }


    }
}
