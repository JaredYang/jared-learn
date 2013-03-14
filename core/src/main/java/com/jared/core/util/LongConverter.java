package com.jared.core.util;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-12
 * Time: 下午6:21
 * To change this template use File | Settings | File Templates.
 */
public class LongConverter implements Converter {
    @Override
    public String convertToString(Object value) {
        return String.valueOf((Long) value);
    }

    @Override
    public Object convertToObject(String value) {
        return Long.valueOf(value);
    }
}
