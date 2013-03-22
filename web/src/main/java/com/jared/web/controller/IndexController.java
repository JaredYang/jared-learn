package com.jared.web.controller;

import com.jared.core.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
         return modelAndView;
    }
}
