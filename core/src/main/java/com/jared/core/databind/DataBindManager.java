package com.jared.core.databind;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-1
 * Time: 下午6:25
 * To change this template use File | Settings | File Templates.
 */
public class DataBindManager {
    private static final DataBindManager INSTANCE = new DataBindManager();

    private final ConcurrentHashMap<Enum, DataBind> map = new ConcurrentHashMap<Enum, DataBind>();

    public static DataBindManager getInstance() {
        return INSTANCE;
    }

    public DataBindManager() {

    }

    public <T> DataBind<T> getDataBind(Enum bindType) {
        DataBind<T> dataBind = map.get(bindType);
        if (dataBind == null) {
            DataBind bind = new ThreadLocalDataBind<T>();
            dataBind = map.putIfAbsent(bindType, bind);
            if (dataBind == null) {
                dataBind = bind;
            }
        }
        return dataBind;
    }

    public enum DataBindType {
        LOGIN_USER,
        USER_CARD;
    }
}
