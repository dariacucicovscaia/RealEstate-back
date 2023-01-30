package com.daria.realestate.controller;

import com.daria.realestate.domain.User;
import com.daria.realestate.dto.RegistrationDTO;
import com.daria.realestate.service.RegistrationService;
import com.daria.realestate.service.UserService;
import com.daria.realestate.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final RegistrationService registrationService;

    public UserController(UserServiceImpl userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public RegistrationDTO register(@RequestBody RegistrationDTO userDTO) {
        return registrationService.registerUser(userDTO);
    }

    @GetMapping("/email/{userEmail}")
    public User getUserByEmail(@PathVariable String userEmail){
        return userService.getUserByEmail(userEmail);
    }
}
