package com.jared.core.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
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


    @RequestMapping(value = {"index"},method = RequestMethod.GET)
    public ModelAndView index(){
        log.info("mapping {}" , "index");
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
        return "";
    }

    @RequestMapping(value = {"json/test"},method = RequestMethod.GET)
    @ResponseBody
    public String jsonTest(String json){
        return "";
    }
}
