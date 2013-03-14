package com.jared.core.util;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-11
 * Time: 下午6:51
 * To change this template use File | Settings | File Templates.
 */
public class BooleanConverter implements Converter {
    @Override
    public String convertToString(Object value) {
        return (Boolean) value ? "1" : "0";
    }

    @Override
    public Object convertToObject(String value) {
        if ("0".equals(value)) {
            return false;
        }
        return true;
    }
}
