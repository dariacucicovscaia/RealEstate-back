package com.daria.realestate.controller;

import com.daria.realestate.domain.User;
import com.daria.realestate.dto.RegistrationDTO;
import com.daria.realestate.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public RegistrationDTO register(@RequestBody RegistrationDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @GetMapping("/email/{userEmail}")
    public User getUserByEmail(@PathVariable String userEmail){
        return userService.getUserByEmail(userEmail);
    }
}
