/*
package com.jared.core.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

*/
/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-7-10
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 *//*

public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    public static String ObjectToJSONArray(Object o) {
        return JSONArray.fromObject(o).toString();
    }

    public static String ObjectToJSONObject(Object o) {
        return JSONObject.fromObject(o).toString();
    }

    public static String ListToJSONArray(List list) {
        return JSONArray.fromObject(list).toString();
    }

    public static String ListToJSObject(List list) {
        return JSONSerializer.toJSON(list).toString();
    }

    public static String MapToJSONArray(Map map) {
        return JSONArray.fromObject(map).toString();
    }

    public static String MapToJSONObject(Map map) {
        return JSONObject.fromObject(map).toString();
    }

    public static Object JsonToObject(String json, Object o) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        return JSONObject.toBean(jsonObject, o.getClass());
    }

    public final static void fail(String string) {
        System.out.println(string);
    }

}
*/
