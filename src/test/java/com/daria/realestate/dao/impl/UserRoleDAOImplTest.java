package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.dbconnection.DBConfig;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class UserRoleDAOImplTest {
    private UserRoleDAO userRoleDAO;

    @Before
    public void init() {
        this.userRoleDAO = new UserRoleDAOImpl(new DBConfig().dataSource());
    }

    @Test
    public void testCreationOfUserRole() {
        User user = new User(1L, "mariana", "passwprd");
        Role role = Role.USER;
        Role createdUserRole = userRoleDAO.create(user.getId(), role);
        Assert.assertEquals(role, createdUserRole);

        int affectedRows = userRoleDAO.removeRole(user.getId(), createdUserRole);
        Assert.assertEquals(affectedRows, 1);

        affectedRows = userRoleDAO.removeRole(user.getId(), createdUserRole);
        Assert.assertEquals(affectedRows, 0);
    }


    @Test
    public void getAllRolesOfAUser() {
        List<Role> roles = userRoleDAO.getRolesOfAUser(4L);
        roles.forEach(System.out::println);
    }
}
