package com.daria.realestate.service;

import com.daria.realestate.domain.User;

public interface UserService {

    User getUserByEmail(String email);
    User updateUser(User user);
    User getUserById(Long id);
}
