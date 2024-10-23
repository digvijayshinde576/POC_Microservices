package com.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class WelcomeRestController {

    @Autowired
    private Environment environment;

    @GetMapping("/welcome")
    public String getWelcomeMsg(){
        String property = environment.getProperty("server.port");
        String str="Welcome To My World....("+property+")";
        return str;

    }
}
