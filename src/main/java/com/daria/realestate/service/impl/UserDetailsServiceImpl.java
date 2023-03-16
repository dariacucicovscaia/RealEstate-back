package com.daria.realestate.service.impl;

import com.daria.realestate.configuration.security.core.UserDetailsImpl;
import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;

    public UserDetailsServiceImpl(UserDAO userDAO, UserRoleDAO userRoleDAO) {
        this.userDAO = userDAO;
        this.userRoleDAO = userRoleDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByEmail(username);
        user.setRoles(userRoleDAO.getRolesOfAUser(user.getId()));
        return UserDetailsImpl.build(user);
    }
}
