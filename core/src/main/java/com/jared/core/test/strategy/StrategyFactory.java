package com.jared.core.test.strategy;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-16
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
public class StrategyFactory {
    private IStrategy strategy;

    public  StrategyFactory(IStrategy strategy){
        this.strategy = strategy;
    }

    public double getPrice(){
        Integer.parseInt("s");
        return strategy.getPrice();
    }
}
