package com.jared.core.common.util;


import com.jared.core.common.util.constant.UserConstant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletUtil {
    public static final int DEFAULT_PORT = 80;
    public static final int MAX_IP_LENGTH = 15;

    public static final void clearUserCookie(HttpServletRequest request, HttpServletResponse response) {
        deleteCookie(request, response, UserConstant.USER_NAME);
        deleteCookie(request, response, UserConstant.AUTH_COOKIE_KEY);
        deleteCookie(request, response, UserConstant.AUTH_COOKIE_KEY_V2);
        deleteCookie(request, response, UserConstant.AUTH_UID_COOKIE_KEY);
        deleteCookie(request, response, UserConstant.LOGIN_STATUS);
    }

    /**
     * 根据HttpServletRequest获得url
     *
     * @param request 请求
     *
     * @return url
     */
    public static String getUrl(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String url = "http://" + request.getServerName()
                + (request.getServerPort() == DEFAULT_PORT ? "" : ":" + request.getServerPort())
                + request.getContextPath() + request.getServletPath();
        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
            url = url + "?" + HtmlFilterUtil.filterHeaderValue(request.getQueryString());
        }
        return url;
    }

    /**
     * 根据HttpServletRequest获得访问ip
     *
     * @param request 请求
     *
     * @return ip
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || "".equals(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.split(",")[0].trim().length() > MAX_IP_LENGTH) {
            return "";
        }
        return ip.split(",")[0].trim();
    }

    public static Cookie getCookie(HttpServletRequest request, String key) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals(key)) {
                return c;
            }
        }
        return null;
    }

    public static boolean writeCookie(HttpServletResponse response, String name, String value, String domain, int time) {
        try {
            Cookie cookie = new Cookie(name, value);
            cookie.setPath("/");
            if (!domain.equals("localhost")) {
                cookie.setDomain(domain);
            }
            if (time > 0) {
                cookie.setMaxAge(time);
            }
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean writeCookie(HttpServletResponse response, String name, String value, int time) {
        return writeCookie(response, name, value, "www.jared.com", time);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        deleteCookie(request, response, null, name);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String domain, String name) {
        Cookie cookie = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(name)) {
                    c.setValue(null);
                    c.setMaxAge(0);
                    if (domain == null) {
                        domain = "www.jared.com";
                    }
                    if (!domain.equals("localhost")) {
                        c.setDomain(domain);
                    }
                    c.setPath("/");
                    cookie = c;
                }
            }
        }
        if (cookie != null) {
            response.addCookie(cookie);
        }
    }
}
