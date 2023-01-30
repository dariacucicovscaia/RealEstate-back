package com.daria.realestate.service.impl;

import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.dao.impl.UserDAOImpl;
import com.daria.realestate.dao.impl.UserRoleDAOImpl;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.Role;
import com.daria.realestate.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;

    public UserServiceImpl(UserDAOImpl userDAO, UserRoleDAOImpl userRoleDAO) {
        this.userDAO = userDAO;
        this.userRoleDAO = userRoleDAO;
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
        User user = userDAO.getById(id);
        user.setRoles(userRoleDAO.getRolesOfAUser(id));
        return user;
    }
@Override
    public User createUser(User user) {
        return userDAO.create(user);
    }

}
