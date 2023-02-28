package com.daria.realestate.controller;

import com.daria.realestate.dto.LoginDTO;
import com.daria.realestate.configuration.security.jwt.JWTProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {
    private final JWTProvider jwtProvider;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    public AuthController(JWTProvider jwtProvider, AuthenticationManager authManager, UserDetailsService userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        String token = jwtProvider.createToken(authentication);
        return ResponseEntity.ok(token);

    }
}
