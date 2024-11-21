package com.miliancode.carblog.controllers;

import com.miliancode.carblog.appuser.AppUser;
import com.miliancode.carblog.repo.AppUserRepository;
import com.miliancode.carblog.services.RecoveryPasswordService;
import com.miliancode.carblog.services.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PasswordRecoveryController {

    private AppUserRepository appUserRepository;
    private RecoveryPasswordService recoveryPasswordService;
    private ConfirmationTokenService confirmationTokenService;

    @GetMapping("/recovery")
    public String passwordRecovery(){
        return "recover-password-email";
    }

    @PostMapping("/recoverequest")
    public String passwordRecoveryMail(@RequestParam("email") String email, Model model) {
        if (appUserRepository.findByEmail(email).isEmpty()){
            model.addAttribute("incorrectEmail", "There is no account registered with this email");
            return "recover-password-email";
        }
        model.addAttribute("sentMail", "Email was sent to " + email);
        recoveryPasswordService.recoveryPasswordTokenSend(email);

        return "recover-password-email";
    }

    @GetMapping("recovery/password")
    public String passwordRecoveryConfirm(@RequestParam("token") String token) {

        if (confirmationTokenService.getToken(token).isEmpty() || confirmationTokenService.getToken(token).get().getConfirmedAt() != null){
            return "error-page";
        }

        return "recover-password";
    }

    @PostMapping("recovery/password")
    public String passwordRecoveryForm(@RequestParam("token") String token, @RequestParam("password") String password, Model model) {
        AppUser user = confirmationTokenService.getToken(token).get().getAppUser();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(passwordEncoder.encode(password));

        appUserRepository.save(user);

        return "redirect:/login";
    }
}
