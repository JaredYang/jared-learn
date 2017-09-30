package com.jared.core.design.patterns.LSP;

/**
 * Created by yangjunde on 2017/9/29.
 *
 * LSP 里氏替换原则(Liskov Substitution Principle,LSP)
 * 所有引用基类的地方必须透明的使用其子类的对象
 * 子类必须完全实现父类的方法
 * 开闭原则是目标，里氏代换原则是基础，依赖倒转原则是手段
 */
public class Client {

	public static void main(String[] args) {
		Soldier soldier = new Soldier();
		soldier.setGun(new HandGun());
		soldier.shoot();

		soldier.setGun(new Rifle());
		soldier.shoot();

		soldier.setGun(new MachineGun());
		soldier.shoot();
	}
}
