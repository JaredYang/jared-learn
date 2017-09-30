package com.jared.core.design.patterns.LSP;

/**
 * Created by yangjunde on 2017/9/29.
 */
public class Soldier {

	private AbstractGun gun;

	public void setGun(AbstractGun gun) {
		this.gun = gun;
	}

	public void shoot(){
		System.out.println("士兵开始射击");
		this.gun.shoot();
	}




}
