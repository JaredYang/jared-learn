package com.jared.core.service.Impl;

import com.jared.core.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-6-4
 * Time: 下午6:43
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String say(String s) {
        return s;
    }
}
