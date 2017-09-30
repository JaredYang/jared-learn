package com.jared.core.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangjunde on 2017/9/25.
 */
public class Test1 {
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
    public Lock lock = new ReentrantLock();
    public  Condition A = lock.newCondition();
    public  Condition B = lock.newCondition();
    public  Condition C = lock.newCondition();
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
            majusculeABC.lock.lock();
            if (name.equals("A")){
                if (majusculeABC.total.get() % 3 != 0){
                    try {
                        majusculeABC.A.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    majusculeABC.printMajuscule(name);
                    count --;
                    majusculeABC.B.signal();
                    majusculeABC.C.signal();
                }
            }
            if (name.equals("B")){
                if (majusculeABC.total.get() % 3 != 1){
                    try {
                        majusculeABC.B.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    majusculeABC.printMajuscule(name);
                    count --;
                    majusculeABC.A.signal();
                    majusculeABC.C.signal();
                }
            }
            if (name.equals("C")){
                if (majusculeABC.total.get() % 3 != 2){
                    try {
                        majusculeABC.C.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    majusculeABC.printMajuscule(name);
                    count --;
                    majusculeABC.A.signalAll();
                    majusculeABC.B.signalAll();
                }
            }
            majusculeABC.lock.unlock();

        }


    }
}

