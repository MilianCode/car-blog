package com.bobocode.springbootdemo.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

//    @GetMapping("/news")
//    public String home(Model model) {
//        model.addAttribute("title", "main page");
//        return "home";
//    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Page that describe us");
        return "about";
    }

}
