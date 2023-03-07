package com.daria.realestate.service.impl;

import com.daria.realestate.configuration.security.jwt.JWTProvider;
import com.daria.realestate.dto.LoginDTO;
import com.daria.realestate.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final JWTProvider jwtProvider;
    private final AuthenticationManager authManager;

    public AuthServiceImpl(JWTProvider jwtProvider, AuthenticationManager authManager) {
        this.jwtProvider = jwtProvider;
        this.authManager = authManager;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return jwtProvider.createToken(authentication);
    }
}
