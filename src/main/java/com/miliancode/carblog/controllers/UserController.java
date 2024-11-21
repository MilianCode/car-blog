package com.miliancode.carblog.controllers;

import com.miliancode.carblog.appuser.AppUser;
import com.miliancode.carblog.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/account/{id}")
    public String userInfo(@PathVariable(value = "id") Long id, Model model){
        if (!appUserRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<AppUser> user = appUserRepository.findById(id);
        ArrayList<AppUser> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "account-info";
    }

}
