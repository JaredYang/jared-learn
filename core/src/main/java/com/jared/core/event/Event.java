package com.jared.core.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Created by yangjunde on 2016/11/14.
 */
public class Event {

    @Subscribe
    public void subMsg(String msg){
        System.out.println(msg);
    }

    @Subscribe
    public void subInteger(Integer status){
        System.out.println("" + status);
    }

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new Event());
        eventBus.post("test");
        eventBus.post(1);
    }

}
