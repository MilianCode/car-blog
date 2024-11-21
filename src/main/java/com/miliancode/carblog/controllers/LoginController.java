package com.miliancode.carblog.controllers;

import com.miliancode.carblog.appuser.AppUser;
import com.miliancode.carblog.repo.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class LoginController {

    AppUserRepository appUserRepository;

    @GetMapping("/login")
    public String logIn() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (appUserRepository.findByEmail(email).isEmpty()){
            model.addAttribute("incorrectEmail", "There is no account registered with this email");
            return "login";
        }

        AppUser user = appUserRepository.loadByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("incorrectPassword", "Incorrect password");
            return "login";
        }

        if (!user.isEnabled()) {
            model.addAttribute("notEnable", "Account not activated");
            return "login";
        }

        return "redirect:/account/" + user.getId();
    }
}
