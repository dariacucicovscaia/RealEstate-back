package com.daria.realestate.service;

import com.daria.realestate.dto.LoginDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
}
