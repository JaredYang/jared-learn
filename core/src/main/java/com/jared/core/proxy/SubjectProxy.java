package com.jared.core.proxy;

/**
 * Created by yangjunde on 16/10/20.
 */
public class SubjectProxy implements Subject {
    private Subject subject = new RealSubject();

    @Override
    public void doSomething() {
        System.out.println("I am proxy");
        subject.doSomething();
        System.out.println("Yo Yo.....");
    }
}
