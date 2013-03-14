package com.jared.core.util;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-11
 * Time: 下午6:38
 * To change this template use File | Settings | File Templates.
 */
public interface Converter {
    String convertToString(Object value);

    Object convertToObject(String value);
}
