package com.jared.core.util;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-11
 * Time: 下午6:44
 * To change this template use File | Settings | File Templates.
 */
public class StringConverter implements Converter {
    @Override
    public String convertToString(Object value) {
        return String.valueOf(value);
    }

    @Override
    public Object convertToObject(String value) {
        return value;
    }
}
