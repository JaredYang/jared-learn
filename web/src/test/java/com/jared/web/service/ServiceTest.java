package com.jared.web.service;

import com.jared.core.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-1-29
 * Time: 下午3:49
 * To change this template use File | Settings | File Templates.
 */
public class ServiceTest {
    //@Test
    public void peopleServiceTest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.say("hello world");

    }
}
