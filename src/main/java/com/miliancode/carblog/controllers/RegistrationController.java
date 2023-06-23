package com.miliancode.carblog.controlers;

import com.miliancode.carblog.services.RegistrationRequest;
import com.miliancode.carblog.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/demo/registration")
@AllArgsConstructor
public class RegistrationController {


    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
