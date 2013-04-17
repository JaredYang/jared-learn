package com.jared.core.test.caller;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-12
 * Time: 下午7:07
 * To change this template use File | Settings | File Templates.
 */
public class MyCaller {
    private MyCallInterface myCallInterface;

    public MyCaller(MyCallInterface myCallInterface){
        this.myCallInterface = myCallInterface;
    }

    public void call(){
        myCallInterface.method();
    }
}
