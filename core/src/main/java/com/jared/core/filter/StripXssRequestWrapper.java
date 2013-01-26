package com.jared.core.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Map;

/**
 * User: jiangxu.qiu
 * Date: 13-1-15
 * Time: 下午5:12
 */
public class StripXssRequestWrapper extends HttpServletRequestWrapper implements HttpServletRequest {

    private Map<String, String[]> requestMap;

    public StripXssRequestWrapper(HttpServletRequest request, Map<String, String[]> requestMap) {
        super(request);
        this.requestMap = requestMap;
    }

    @Override
    public String getParameter(String name) {
        String[] values = requestMap.get(name);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return null;
    }

    @Override
    public Map getParameterMap() {
        return requestMap;
    }

    @Override
    public Enumeration getParameterNames() {
        return super.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return requestMap.get(name);
    }
}
