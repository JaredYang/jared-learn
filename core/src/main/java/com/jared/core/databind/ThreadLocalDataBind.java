package com.jared.core.databind;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-1
 * Time: 下午6:22
 * To change this template use File | Settings | File Templates.
 */
public class ThreadLocalDataBind<T> implements DataBind<T> {
    private final ThreadLocal<T> threadLocal = new ThreadLocal<T>();
    @Override
    public void put(T t) {
        threadLocal.set(t);
    }

    @Override
    public T get() {
        return threadLocal.get();
    }

    @Override
    public void remove() {
        threadLocal.remove();
    }
}
