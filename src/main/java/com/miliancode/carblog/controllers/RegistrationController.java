package com.miliancode.carblog.controllers;

import com.miliancode.carblog.services.RegistrationRequest;
import com.miliancode.carblog.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/register")
    public String registration() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model){
        RegistrationRequest request = new RegistrationRequest(username, email, password);
        model.addAttribute("sentMail", "Confirmation email was sent");
        registrationService.register(request);
        return "register";
    }

    @GetMapping("/register/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
