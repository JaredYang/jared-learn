package com.jared.core.filter;


import com.jared.core.common.util.ParamXssStripper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-1-25
 * Time: 下午6:30
 * To change this template use File | Settings | File Templates.
 */
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }
        Map<String, String[]> filteredMap = new HashMap<String, String[]>();
        Map originalMap = request.getParameterMap();
        if (originalMap != null && originalMap.size() > 0) {
            for (Object o : originalMap.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                String name = (String) entry.getKey();
                String[] originalValues = (String[]) entry.getValue();
                if (originalValues == null) {
                    continue;
                }
                if (originalValues.length <= 0) {
                    filteredMap.put(name, originalValues);
                    continue;
                }
                String[] filteredValues = new String[originalValues.length];
                int index = 0;
                for (String originalValue : originalValues) {
                    filteredValues[index++] = ParamXssStripper.filter(originalValue);
                }
                filteredMap.put(name, filteredValues);
            }
        }
        chain.doFilter(new StripXssRequestWrapper((HttpServletRequest) request, filteredMap), response);
    }

    @Override
    public void destroy() {
    }
}
