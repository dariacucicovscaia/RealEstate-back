package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.Role;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserRoleDAOImplTest extends AbstractPropsSet{
    @Autowired
    private UserRoleDAO userRoleDAO;


    @Test
    public void testCreationOfUserRole() {
        User user = new User(100000000L,"mariana", "passwprd");
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
        User user = new User(100000001L,"mariana", "passwprd");
        Role role = Role.USER;
        userRoleDAO.create(user.getId(), role);
        List<Role> roles = userRoleDAO.getRolesOfAUser(100000001L);
        Assert.assertEquals(roles.size(), 1);
    }
}
