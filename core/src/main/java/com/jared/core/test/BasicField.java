package com.jared.core.test;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-2
 * Time: 上午11:06
 * To change this template use File | Settings | File Templates.
 */
public class BasicField {
    public int i = 5;

    public int changeFiled(int i){
        i = 10;
        return i;
    }

    public static void main(String[] args) {
        BasicField b = new BasicField();
        b.changeFiled(b.i);
        System.out.println(b.i);
    }
}
