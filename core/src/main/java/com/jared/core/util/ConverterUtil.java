package com.jared.core.util;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-12
 * Time: 下午6:23
 * To change this template use File | Settings | File Templates.
 */
public class ConverterUtil {
    public static Converter getConverter(Class<?> type) {
        if (type == Date.class) {
            return new DateConverter();
        }
        if (type == String.class) {
            return new StringConverter();
        }
        if (type == Integer.class) {
            return new IntegerConverter();
        }
        if (type == Long.class) {
            return new LongConverter();
        }
        if (type == Boolean.class) {
            return new BooleanConverter();
        }
        return null;
    }
}
