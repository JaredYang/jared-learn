package com.jared.core.test.singleton;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-17
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public class Singleton {
    private static Singleton instance;

    private Singleton() {

    }

    public static Singleton getInstance() {
        synchronized (Singleton.class) {
            if (instance == null) {
                instance = new Singleton();
            }
            return instance;
        }

    }

}
