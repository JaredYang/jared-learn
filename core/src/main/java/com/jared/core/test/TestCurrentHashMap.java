package com.jared.core.test;

import com.jared.core.model.People;

import java.util.concurrent.ConcurrentHashMap;

public class TestCurrentHashMap {
	public static void main(String[] args) {
		ConcurrentHashMap map = new ConcurrentHashMap();
        People p1 = new People();
        p1.setId(1);
        People p2 = new People();
        p2.setId(2);
        People p3 = new People();
        p3.setId(3);
		map.putIfAbsent("1", p1);
		map.putIfAbsent("2", p2);
        map.putIfAbsent("1",p3);
        map.put("3","1");
	}

}
