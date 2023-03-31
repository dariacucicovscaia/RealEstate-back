package com.daria.realestate.controller;

import com.daria.realestate.dto.LoginDTO;
import com.daria.realestate.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
//todo dto instead of hashmap
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        Map<String , String > result = new HashMap<>();
        result.put("token", authenticationService.login(loginDTO));
        result.put("type", "Bearer ");
        return ResponseEntity.ok(result);
    }
}
