package com.daria.realestate.service;

import com.daria.realestate.domain.User;
import com.daria.realestate.dto.RegistrationDTO;

public interface UserService {
    RegistrationDTO registerUser(RegistrationDTO registrationDTO);
    User getUserByEmail(String email);
    User updateUser(User user);
    User getUserById(Long id);
}
