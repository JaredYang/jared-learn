package com.jared.core.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: lifeng.xu
 * Date: 11-11-23
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
public class UserAgentUtil {

    private final static Log logger = LogFactory.getLog(UserAgentUtil.class);
    private final static Pattern IE_P = Pattern.compile("MSIE (\\d)");
    private final static Pattern FF_P = Pattern.compile("Firefox/(\\d)");

    public static String[] getUA(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return getUA(ua);
    }

    public static String[] getUA(String ua) {
        if (StringUtil.isEmpty(ua))
            return new String[]{null, null};
        String os = null;
        String browser = null;
        if (ua.contains("Win")) {
            os = "ua-win";
        } else if (ua.contains("Mac")) {
            os = "ua-mac";
        }

        Matcher iem = IE_P.matcher(ua);
        if (iem.find()) {
            browser = "ua-ie" + iem.group(1);
        } else {
            Matcher ffm = FF_P.matcher(ua);
            if (ffm.find()) {
                browser = "ua-ff" + ffm.group(1);
            } else {
                if (ua.contains("AppleWebKit")) {
                    browser = "ua-wk";
                }
            }
        }
        StringBuffer uaLog = new StringBuffer("User Agent info : ");
        uaLog.append(ua).append(". And Result:").append(os).append(";").append(browser);
        logger.info(uaLog.toString());
        return new String[]{os, browser};
    }
}
