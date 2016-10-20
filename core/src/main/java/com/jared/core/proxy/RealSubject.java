package com.jared.core.proxy;

/**
 * Created by yangjunde on 16/10/20.
 */
public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println("do something");
    }
}
