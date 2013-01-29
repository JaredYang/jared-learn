package com.jared.web.controller;

import com.jared.core.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-1-25
 * Time: 下午7:18
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("jared")
public class IndexController {
      private UserService userService;
    //-Xmx512m -XX:MaxPermSize=128m
}
