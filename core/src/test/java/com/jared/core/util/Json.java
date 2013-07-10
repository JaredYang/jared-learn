package com.jared.core.util;

import com.jared.core.json.JsonUtil;
import com.jared.core.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-7-10
 * Time: 下午2:46
 * To change this template use File | Settings | File Templates.
 */
public class Json {
    private String[] strArr;
    private Boolean[] booleanArr;
    private User user1;
    private User user2;
    List<User> userList = new ArrayList<User>();

    @Before
    public void init() {
        strArr = new String[]{"1", "2", "3"};
        booleanArr = new Boolean[]{true, false, true};
        user1 = new User();
        user1.setId(1);
        user1.setAge(12);
        user1.setName("jared");
        user2 = new User();
        user2.setId(2);
        user2.setAge(20);
        user2.setName("yang");
        userList.add(user1);
        userList.add(user2);
    }

    @Test
    public void testObjectToJson() {
        JsonUtil.fail("string array to json array");
        JsonUtil.fail(JsonUtil.ObjectToJSONArray(strArr));
        JsonUtil.fail("boolean array to json array");
        JsonUtil.fail(JsonUtil.ObjectToJSONArray(booleanArr));
        JsonUtil.fail("object to json array");
        JsonUtil.fail(JsonUtil.ObjectToJSONArray(user1));
        JsonUtil.fail("object to json object");
        JsonUtil.fail(JsonUtil.ObjectToJSONObject(user1));
    }

    @Test
    public void testObjectToList() {
        JsonUtil.fail("list to json array");
        JsonUtil.fail(JsonUtil.ListToJSONArray(userList));
        JsonUtil.fail("list to json object");
        JsonUtil.fail(JsonUtil.ListToJSObject(userList));
    }

    @Test
    public void testJsonToObject() {
        //json 数组字符串 转换成 object对象
        String jsonStr = JsonUtil.ObjectToJSONArray(strArr);
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        Object object = JSONArray.toArray(jsonArray);

        //json字符串转换成对象
        String userJson = JsonUtil.ObjectToJSONObject(user1);
        JSONObject userObject = JSONObject.fromObject(userJson);
        User user = (User) JSONObject.toBean(userObject, User.class);
        JsonUtil.fail(user.getName());
        //json字符串转换成数组对象
        String userArrayJson = JsonUtil.ListToJSONArray(userList);
        JSONArray userListArray = JSONArray.fromObject(userArrayJson);
        User[] userArr = (User[]) JSONArray.toArray(userListArray, User.class);

        List<User> con = (List<User>)JSONArray.toCollection(userListArray,User.class);
    }

}
