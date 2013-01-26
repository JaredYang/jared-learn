package com.jared.core.common.util;


public class AjaxUtil {
    /**
     * ajax返回值，code和msg以冒号分隔
     *
     * @param code 0为成功，不需要描述信息，直接返回0，大于0的，msg不能为空
     * @param msg  描述信息
     * @return ajax响应字符串
     */
    public static String response(int code, String msg) {
        return code == 0 ? "0" : code + ":" + msg;
    }
}
