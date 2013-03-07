package com.jared.core.test;

import java.util.concurrent.ConcurrentHashMap;

public class TestCurrentHashMap {
	public static void main(String[] args) {
		ConcurrentHashMap map = new ConcurrentHashMap();
		map.putIfAbsent("", "");
		
	}

}
