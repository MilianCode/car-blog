package com.miliancode.carblog.controlers;

import com.miliancode.carblog.appuser.AppUser;
import com.miliancode.carblog.appuser.AppUserRepository;
//import com.miliancode.carblog.dao.UserDAO;
import com.miliancode.carblog.models.User;
import com.miliancode.carblog.repo.UserRepository;

import com.miliancode.carblog.services.RegistrationRequest;
import com.miliancode.carblog.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private UserDAO userDAO;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AppUserRepository appUserRepository;

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


    @GetMapping("/register")
    public String registration() {
        return "register";
    }

//    @PostMapping("/register")
//    public String processRegistration(@RequestParam String username,
//                               @RequestParam String email,
//                               @RequestParam String password) {
//        User user = new User(username, email, password);
//        userRepository.save(user);
//        return "redirect:/account/" + user.getId();
//    }

//    @PostMapping("/register")
//    public String register(@RequestBody RegistrationRequest request){
//        registrationService.register(request);
//        return "login";
//    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model
                           ){
        RegistrationRequest request = new RegistrationRequest(username, email, password);
        model.addAttribute("sentMail", "Confirmation email was sent");
        registrationService.register(request);

        return "register";
    }

    @GetMapping("/register/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

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

    @GetMapping("/recovery")
    public String passwordRecovery(){
        return "recover-password-email";
    }

    @PostMapping("/recoverequest")
    public String passwordRecoveryMail(@RequestParam("email") String email, Model model) {
        //TODO: send mail with recovery mail
        if (appUserRepository.findByEmail(email).isEmpty()){
            model.addAttribute("incorrectEmail", "There is no account registered with this email");
            return "recover-password-email";
        }


        model.addAttribute("sentMail", "Email was sent to " + email);
        return "recover-password-email";
    }


}
