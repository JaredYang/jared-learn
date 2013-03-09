package com.jared.core.serialize;

import com.jared.core.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-9
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 */
public class Serialize {
    public byte[] serializeList(List<User> value) {
        if (value == null) {
            throw new NullPointerException("can not serialize null");
        }
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            for (User user : value) {
                oos.writeObject(user);
            }
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public List<User> deserialize(byte[] in) {
        List<User> userList = new ArrayList<User>();
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(in);
            ois = new ObjectInputStream(bis);
            while (true) {
                User user = (User) ois.readObject();
                if (user == null) {
                    break;
                } else {
                    userList.add(user);
                }
            }
            ois.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                ois.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }
}
