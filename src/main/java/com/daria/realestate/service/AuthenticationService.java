package com.daria.realestate.service;

import com.daria.realestate.dto.LoginDTO;

public interface AuthenticationService {
    String login(LoginDTO loginDTO);
}
