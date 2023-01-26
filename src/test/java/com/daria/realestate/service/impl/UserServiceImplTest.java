package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.dao.impl.UserDAOImpl;
import com.daria.realestate.dao.impl.UserRoleDAOImpl;
import com.daria.realestate.domain.User;
import com.daria.realestate.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;


public class UserServiceImplTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private UserRoleDAO userRoleDAO;

    private UserService serviceUnderTests;


    @Before
    public void setupService() {
        userDAO = mock(UserDAOImpl.class);
        userRoleDAO = mock(UserRoleDAOImpl.class);
        serviceUnderTests = new UserServiceImpl((UserDAOImpl) userDAO, (UserRoleDAOImpl) userRoleDAO);
    }

    @Test
    public void getUserByEmail() {
        when(userDAO.getUserByEmail("email")).thenReturn(new User(1L, "email", "password"));
        User user = serviceUnderTests.getUserByEmail("email");
        Assert.assertNotNull(user);
        verify(userDAO).getUserByEmail("email");
    }

    @Test
    public void update() {
        User user = new User(1L, "mariana@gmail.com", "123456");
        when(userDAO.update(user)).thenReturn(user);

        User updatedTestData = serviceUnderTests.updateUser(user);

        verify(userDAO).update(user);
        Assert.assertNotNull(updatedTestData);
    }

    @Test
    public void getById() {
        when(userDAO.getById(1L)).thenReturn(new User(1L, "mariana@gmail.com", "123456"));

        User fetchedUser = serviceUnderTests.getUserById(1L);

        verify(userDAO).getById(1L);
        Assert.assertNotNull(fetchedUser);
    }
}