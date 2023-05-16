package com.miliancode.carblog.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String logIn() {
        return "login";
    }

    @GetMapping("/register")
    public String registration() {
        return "register";
    }
}
