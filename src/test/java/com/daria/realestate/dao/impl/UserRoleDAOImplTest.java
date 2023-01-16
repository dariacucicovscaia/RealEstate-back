package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.UserRole;
import com.daria.realestate.domain.enums.Roles;
import com.daria.realestate.dbconnection.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class UserRoleDAOImplTest {
    private UserRoleDAO userRoleDAO;

    @Before
    public void init() {
        this.userRoleDAO = new UserRoleDAOImpl(DataBaseConnection.getInstance());
    }

    @Test
    public void testCreationOfUserRole() {
        UserRole createdUserRole = userRoleDAO.create(new UserRole(new User(1L, "email", "password"), Roles.ADMIN));
        Assert.assertTrue(createdUserRole.getId() != 0);
        userRoleDAO.removeById(createdUserRole.getId());
    }

    @Test
    public void testFetchOfAUserRole() {
        UserRole createdUserRole = userRoleDAO.getById(1);
        Assert.assertTrue(createdUserRole.getRole().equals(Roles.SELLER));
    }

    @Test
    public void getAllRolesOfAUser() {
        List<Roles> roles = userRoleDAO.getRolesOfAUser(new User(4L, "email", "password"));
        Assert.assertTrue(roles.size() >= 1 && roles.size() <= 3);
    }
}
