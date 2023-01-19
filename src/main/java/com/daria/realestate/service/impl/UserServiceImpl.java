package com.daria.realestate.service.impl;

import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.impl.UserDAOImpl;
import com.daria.realestate.domain.User;
import com.daria.realestate.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        return userDAO.update(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getById(id);
    }


}
