package com.d.clinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String showHome(){
        return "index";
    }

    @RequestMapping("/login")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/error-access")
    public String accessDenied() {
        return "error";
    }


}
