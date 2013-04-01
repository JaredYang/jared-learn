package com.jared.core.databind;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-1
 * Time: 下午6:20
 * To change this template use File | Settings | File Templates.
 */
public interface DataBind<T> {

    void  put(T t);

    T get();

    void remove();
}
