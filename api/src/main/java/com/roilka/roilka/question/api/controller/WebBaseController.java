package com.roilka.roilka.question.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName WebBaseController
 * @Description TODO
 * @Author changyou
 * @Date 2019/11/26 16:15
 **/
@Controller
public class WebBaseController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
}
