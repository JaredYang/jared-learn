package com.jared.core.test.strategy;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-16
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
public class StrategyFirst implements IStrategy {
    @Override
    public double getPrice() {
        return 1;
    }
}
