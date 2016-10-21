package com.jared.core.proxy;

/**
 * Created by yangjunde on 16/10/20.
 */
public class ProxyTest {

    public static void main(String[] args) {
       /* Subject subject = new SubjectProxy();
        subject.doSomething();*/

        ProxyHandler proxyHandler = new ProxyHandler();
        Subject subject1 = (Subject)proxyHandler.bind(new RealSubject());
        subject1.doSomething();
    }
}
