package com.jared.core.test.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-12
 * Time: 下午7:55
 * To change this template use File | Settings | File Templates.
 */
public class Singleton {
    private static Singleton singleton;
    private Singleton(){
    }
    public Singleton getInstance(){
        synchronized (Singleton.class){
            if (singleton==null){
                singleton = new Singleton();
            }
            return singleton;
        }
    }
}
