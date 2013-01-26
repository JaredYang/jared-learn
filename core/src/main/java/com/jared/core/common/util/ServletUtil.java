package com.jared.core.common.util;


import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;

/**
 * User: hui.ouyang
 * Date: 2010-2-25
 * Time: 14:15:24
 */
public class ServletUtil {
    private final static Log log = LogFactory.getLog(ServletUtil.class);
    public static final String CACHE_ONTROL_RESPONSE_HEADER = "Cache-control";
    public static final String REGEX_CRLF = "\r\n";
    public static final char CR_CH = '\r';

    /**
     * 根据HttpServletRequest获得url
     *
     * @param request 请求
     * @return url
     */
    public static String url(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder(request.getRequestURL());
        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
            stringBuilder.append("?").append(request.getQueryString());
        }
        return stringBuilder.toString();
    }

    /**
     * 根据HttpServletRequest获得访问ip
     *
     * @param request 请求
     * @return ip
     */
    public static String ip(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || "".equals(ip)) {
            ip = request.getRemoteAddr();
        }
        String ipSplitStr = ip.split(",")[0].trim();
        if (ipSplitStr.length() > 15) {
            return "";
        }
        return ipSplitStr;
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
        if (domain == null) {
            throw new IllegalArgumentException("please get value from config for domains.root");
        }

        name = HtmlFilterUtil.filterHeaderName(name);
        value = HtmlFilterUtil.filterHeaderValue(value);
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
            log.error("write cookie(" + name + ":" + value + ") error:" + e.getMessage());
            return false;
        }
    }

    @Deprecated
    public static void deleteCookie(HttpServletResponse response, String name) {
        deleteCookie(response, "dajie.com", name);
    }

    @Deprecated
    public static void deleteCookie(HttpServletResponse response, String domain, String name) {
        if (domain == null) {
            throw new IllegalArgumentException("please get value from config for domains.root");
        }

        name = HtmlFilterUtil.filterHeaderName(name);
        ActionContext context = ActionContext.getContext();
        HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
        Cookie cookie = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(name)) {
                    c.setValue(null);
                    c.setMaxAge(0);
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

    public static void deleteCookie(HttpServletResponse response, HttpServletRequest request, String domain, String name) {
        if (domain == null) {
            throw new IllegalArgumentException("please get value from config for domains.root");
        }

        name = HtmlFilterUtil.filterHeaderName(name);
        Cookie cookie = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(name)) {
                    c.setValue(null);
                    c.setMaxAge(0);
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


    /**
     * 设置response的header，会对输入内容过滤，保证内容中不含CRLF
     * @param response   response
     * @param headerName    headerName
     * @param headerValue   headerValue
     */
    public static void  setResponseHeader(HttpServletResponse response, String headerName, String headerValue) {
        String name = headerName;
        String value = headerValue;
        if(name==null||value==null){
            return;
        }
        if(name.indexOf(CR_CH)>-1) {
            name = name.replaceAll(REGEX_CRLF,"");
        }
        if(value.indexOf(CR_CH)>-1){
            value = value.replaceAll(REGEX_CRLF,"");
        }
        response.setHeader(name,value);
    }

    /**
     * 设置response header中的Cache-control，内容为字符串
     * @param response    response
     * @param headerValue   内容
     */
    public static void  setCacheControlResponseHeader(HttpServletResponse response, String headerValue) {
        String value = headerValue;
        if(value==null){
            return;
        }

        if(value.indexOf(CR_CH)>-1){
            value = value.replaceAll(REGEX_CRLF,"");
        }
        response.setHeader(CACHE_ONTROL_RESPONSE_HEADER,value);
    }

    /**
     * 设置response header中的Cache-control，内容为字符串
     * @param response    response
     * @param sMaxageAge   时长
     */
    public static void  setCacheControlSmaxageResponseHeader(HttpServletResponse response, long sMaxageAge) {
        response.setHeader(CACHE_ONTROL_RESPONSE_HEADER,"s-maxage="+sMaxageAge);
    }

    public static String getWebRoot() {
        ActionContext actionContext = ActionContext.getContext();
        if (actionContext == null) {
            log.debug("There is no actionContext.The invoke should be from struts http request.");
            return null;
        }
        ServletContext servletContext = ServletActionContext.getServletContext();
        String webRoot = null;
        if (servletContext != null) {
            webRoot = ServletActionContext.getServletContext().getRealPath("/").replaceAll("\\\\", "/");
            log.debug("====>>>>> The root path of web context is " + webRoot);
        } else {
            log.debug("====>>>>> There is no servlet context");
        }
        return webRoot;
    }

    public static String getClassesRoot() {
        String webRoot = getWebRoot();
        if (webRoot != null) {
            return webRoot + "/WEB-INF/classes";
        } else {
            webRoot = Thread.currentThread().getContextClassLoader().getResource("configs.properties").getPath();
            if (webRoot != null) {
                webRoot = webRoot.substring(0, webRoot.indexOf("configs.properties"));
            }
            log.debug("Set WebROOT:" + webRoot);
            return webRoot;
        }
    }

    public static String getContextRoot() {
        String contextRoot = getWebRoot();
        if (StringUtil.isEmpty(contextRoot)) {
            log.debug("The web context is empty,try application context root.");
            URL contextUrl = Thread.currentThread().getContextClassLoader().getResource("");
            if (contextUrl != null) {
                contextRoot = contextUrl.getPath();
                log.debug("Find root path of application context success.The path is " + contextRoot);
            } else {
                log.error("Bad luck. Find root path of application context failed. try to use context.root configs");
                // contextRoot = AppConfigs.getInstance().get("context.root");
            }
        }
        return contextRoot;
    }

    public static boolean isCrossDomain(HttpServletRequest request) {
        String host = request.getServerName().toLowerCase();
        String referer = request.getHeader("Referer");
        return referer == null || !referer.toLowerCase().startsWith("http://" + host);
    }
}
