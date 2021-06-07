package io.mkth.registration.registration.controller;

import io.mkth.registration.registration.model.RegistrationRequest;
import io.mkth.registration.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public String register(@RequestBody RegistrationRequest request){
        return this.registrationService.register(request);
    }

    @PostMapping("/registration/emailConfirmation")
    public String verifyEmail(@RequestParam("token") String token) {
        return this.registrationService.verifyEmail(token);
    }
}
