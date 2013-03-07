package com.jared.core.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
	private final Lock lock = new ReentrantLock(); //锁对象
	private final Condition notFull = lock.newCondition();//写线程条件
	private final Condition notEmpty = lock.newCondition();//读线程条件
	int putptr, takeptr, count;
	final Object[] items = new Object[10];

	public void put(Object o) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length) {
				notFull.await();//阻塞写线程
			}
			items[count] = o;
			System.out.println(" put the " + (putptr + 1));
			if (++putptr == items.length) {
				putptr = 0;
			}
			++count;
			
			notEmpty.signal();//唤醒读线程
		} finally {
			lock.unlock();
		}

	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.await();
			}
			Object o = items[takeptr];
			System.out.println(" take the " + (takeptr + 1));
			if (++takeptr == items.length) {
				takeptr = 0;
			}
			--count;
			
			notFull.signal();
			return o;
		} finally {
			lock.unlock();
		}

	}

	public void test() {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			threadPool.execute(new Runnable() {
				public void run() {
					try {
						Thread.sleep(1000);
						Object o = "a";
						put(o);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		threadPool.shutdown();
		Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {

			public void run() {
            try {

                take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			}
			
		});
	}

	public static void main(String[] args) {
		TestLock test = new TestLock();
		test.test();
	}

}
