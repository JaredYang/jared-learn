package com.jared.core.util;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-12
 * Time: 下午5:57
 * To change this template use File | Settings | File Templates.
 */
public class DateConverter implements Converter {
    @Override
    public String convertToString(Object value) {
        return String.valueOf(((Date)value).getTime());
    }

    @Override
    public Object convertToObject(String value) {
        long time = Long.valueOf(value);
        Date date = new Date();
        date.setTime(time);
        return date;
    }
}
