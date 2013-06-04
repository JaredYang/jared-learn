package com.jared.core.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-5-31
 * Time: 上午11:07
 * To change this template use File | Settings | File Templates.
 */
public class TestClassLoader {
    public static void main(String[] args) {
        TestClassLoader testClassLoader = new TestClassLoader();
        String s = new String("s");
        Map map = new HashMap ();
        System.out.println(testClassLoader.getClass().getClassLoader());
        System.out.println(s.getClass().getClassLoader());
        System.out.println(map.getClass().getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());
    }
}
