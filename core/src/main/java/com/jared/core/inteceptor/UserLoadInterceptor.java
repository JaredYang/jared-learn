/*
package com.jared.core.inteceptor;

import com.jared.core.common.util.AesCryptUtil;
import com.jared.core.common.util.ServletUtil;
import com.jared.core.common.util.StringUtil;
import com.jared.core.common.util.constant.UserConstant;
import com.jared.core.databind.DataBind;
import com.jared.core.databind.DataBindManager;
import com.jared.core.model.UserBase;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-1-25
 * Time: 下午6:31
 * To change this template use File | Settings | File Templates.
 *//*

public class UserLoadInterceptor extends HandlerInterceptorAdapter {
    private static final DataBind<UserBase> LOGIN_USER_DATA_BIND = DataBindManager.getInstance().getDataBind(DataBindManager.DataBindType.LOGIN_USER);
    public static final int MAX_DOMAIN_LENGTH = 12;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserBase userBase = getUserBaseFromV3LoginCookie(request);
        if (userBase == null && !hasV3LoginCookie(request)) {
            userBase = getUserBaseFromV2LoginCookie(request);
        }

        if (userBase != null) {
            request.setAttribute("loginUser", userBase);
            LOGIN_USER_DATA_BIND.put(userBase);
        } else {
            ServletUtil.clearUserCookie(request, response);
        }
        return true;
    }


    private UserBase getUserBaseFromV2LoginCookie(HttpServletRequest request) {
        Cookie cookie = ServletUtil.getCookie(request, UserConstant.AUTH_COOKIE_KEY_V2);
        if (cookie == null || StringUtil.isEmpty(cookie.getValue())) {
            return null;
        }
        String decodeValue = AesCryptUtil.decrypt(cookie.getValue(), UserConstant.AUTH_ENCODE_KEY_V2);
        if (StringUtil.isEmpty(decodeValue) || !StringUtil.isInteger(decodeValue)) {
            return null;
        }
        int uid = Integer.parseInt(decodeValue);
        return null;
    }

    private UserBase getUserBaseFromV3LoginCookie(HttpServletRequest request) {
        Cookie c = ServletUtil.getCookie(request, UserConstant.AUTH_COOKIE_KEY_V3);
        String decode;
        if (c == null
                || StringUtil.isEmpty(c.getValue())
                || StringUtil.isEmpty(decode = AesCryptUtil.decrypt(c.getValue(), UserConstant.AUTH_ENCODE_KEY_V3))) {
            return null;
        }
        String[] info = decode.split("\\|");
        if (info.length != 2 || !StringUtil.isInteger(info[0])) {//info[0]:uid,info[1]:密码
            return null;
        }
        int uid = Integer.parseInt(info[0]);
        if (uid <= 0) {
            return null;
        }
        UserBase userBase = null;
        if(userBase == null || !userBase.getPassword().equalsIgnoreCase(info[1])){
            return null;
        }
        return userBase;
    }

    private boolean hasV3LoginCookie(HttpServletRequest request){
        Cookie c = ServletUtil.getCookie(request, UserConstant.AUTH_COOKIE_KEY_V3);
        return c != null && !StringUtil.isEmpty(c.getValue());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGIN_USER_DATA_BIND.remove();
        super.afterCompletion(request, response, handler, ex);
    }


}
*/
