package com.jared.core.service.impl;

import com.jared.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String say(String s) {
        System.out.println(s);
        log.info("s");
        return s;
    }
}
