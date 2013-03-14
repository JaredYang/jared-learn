package com.jared.core.util;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-12
 * Time: 下午5:54
 * To change this template use File | Settings | File Templates.
 */
public class IntegerConverter implements Converter {
    @Override
    public String convertToString(Object value) {
        return String.valueOf((Integer) value);
    }

    @Override
    public Object convertToObject(String value) {
        return Integer.valueOf(value);
    }
}
