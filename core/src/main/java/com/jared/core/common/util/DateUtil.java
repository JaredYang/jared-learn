package com.jared.core.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: haluo
 * Date: 2010-4-30
 * Time: 16:22:28
 */
public class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);

    private DateUtil() {
    }

    public static long getMinutesRelateToCurrentTime(Date relativeDate) {
        if (relativeDate != null) {
            return (System.currentTimeMillis() - relativeDate.getTime()) / (1000 * 60);
        } else {
            return 0;
        }
    }

    public static long getDaySpan(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            //一天的毫秒数 86400000 = 1000 * 60 * 60 * 24
            return (endDate.getTime() - startDate.getTime()) / 86400000;
        } else {
            return 0;
        }
    }

    public static long getSecondsRelateToCurrentTime(Date relativeDate) {
        if (relativeDate != null) {
            return (System.currentTimeMillis() - relativeDate.getTime()) / (1000);
        } else {
            return 0;
        }
    }

    public static String getDateStrRelateToCurrentDate(Date relativeDate) {
        Calendar current = Calendar.getInstance();
        Calendar create = Calendar.getInstance();
        create.setTime(relativeDate);
        long minutesRelateToCurrentTime = getMinutesRelateToCurrentTime(relativeDate);
        if (minutesRelateToCurrentTime <= 0) {
            minutesRelateToCurrentTime = 1;
        }
        if (minutesRelateToCurrentTime < 2) {
            return minutesRelateToCurrentTime + "分钟内";
        }
        if (minutesRelateToCurrentTime < 60) {
            return minutesRelateToCurrentTime + "分钟前";
        } else if (formatDate(create.getTime(),"yyyy-MM-dd").equalsIgnoreCase(formatDate(current.getTime(),"yyyy-MM-dd"))) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            return "今天 " + formatter.format(relativeDate);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(relativeDate);
        }
    }

    public static String getTimeStrRelateToCurrentTime(Date relativeDate) {
        long minutesRelateToCurrentTime = getMinutesRelateToCurrentTime(relativeDate);
        if (minutesRelateToCurrentTime <= 0) {
            minutesRelateToCurrentTime = 1;
        }
        if (minutesRelateToCurrentTime < 60) {
            return minutesRelateToCurrentTime + "分钟内";
        } else if (minutesRelateToCurrentTime / 60 < 24) {
            return minutesRelateToCurrentTime / 60 + "小时内";
        } else if (minutesRelateToCurrentTime / (60 * 24) < 30) {
            return minutesRelateToCurrentTime / (60 * 24) + "天内";
        } else if (minutesRelateToCurrentTime / (60 * 24 * 30) < 3) {
            return minutesRelateToCurrentTime / (60 * 24 * 30) + "个月内";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(relativeDate);
        }
    }

    public static String getDateSpanStr(Date startDate, Date endDate) {
        long monthSpan = getDaySpan(startDate, endDate) / 30;
        //时间间隔：小于等于24个月时显示“xxx个月”；大于24个月时显示“x年”
        if (monthSpan > 24) {
            return monthSpan / 12 + "年";
        } else {
            return monthSpan + "个月";
        }
    }

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat();
        return format.format(date);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date parseDate(String dateValue, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(dateValue);
        } catch (Exception e) {
            log.error("Warning in DateUtil#parseDate.", e);
            return null;
        }
    }

    public static int getDays(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }
}
