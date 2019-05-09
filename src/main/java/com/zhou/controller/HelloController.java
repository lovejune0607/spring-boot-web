package com.zhou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@ResponseBody
public class HelloController {

    @RequestMapping("/")
    public String index(ModelMap map){
        map.addAttribute("message","http://www.ityouknow.com");
        return "hello";
    }

    @RequestMapping("/hello")
    public String sayHello(String name){
        return "Hello World," +name;
    }

}

