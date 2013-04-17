package com.jared.core.test.caller;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-12
 * Time: 下午7:09
 * To change this template use File | Settings | File Templates.
 */
public class CallBack implements MyCallInterface {
    @Override
    public void method() {
        System.out.println("回调");
    }

    public static void main(String[] args){
        MyCaller myCaller = new MyCaller(new CallBack());
        myCaller.call();
    }
}
