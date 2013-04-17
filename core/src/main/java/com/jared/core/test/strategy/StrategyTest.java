package com.jared.core.test.strategy;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-17
 * Time: 上午10:16
 * To change this template use File | Settings | File Templates.
 */
public class StrategyTest {

    public static void main(String[] args) {
        StrategyFactory strategyFactory = new StrategyFactory(new StrategyFirst());
        double res = strategyFactory.getPrice();
        System.out.println(res);
    }
}
