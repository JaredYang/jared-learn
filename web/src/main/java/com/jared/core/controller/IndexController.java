package com.jared.core.controller;

import com.jared.core.model.User;
import com.jared.core.service.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    /*@Autowired
    public IndexController(UserService userService){
        this.userService = userService;
    }*/

    @RequestMapping(value = {"index"},method = RequestMethod.GET)
    public ModelAndView index(){
        log.info("mapping {}" , "index");
        userService.say("hello");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("hello","helloworld");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = {"get/{key}"},method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("key") Integer key){
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    @RequestMapping(value = {"delete/{key}"},method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable("key") Integer key){
        Map map = new HashMap();
        map.put("uid",12345);
        List<User> userList = new ArrayList<User>();
        User u1 = new User();
        u1.setId(1);
        u1.setAge(12);
        u1.setName("jared");
        userList.add(u1);
        User u2 = new User();
        u2.setId(2);
        u2.setAge(23);
        u2.setName("yang");
        userList.add(u2);
        map.put("userList",userList);
        return JSONObject.fromObject(map).toString();
    }
}
