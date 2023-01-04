package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.dao.impl.UserDAOImpl;
import com.daria.realestate.domain.User;
import com.daria.realestate.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;


public class UserServiceImplTest {
    @Mock
    private UserDAO userDAO;

    private UserService serviceUnderTests;
    private ArgumentCaptor<User> userArgumentsCaptor;


    @Before
    public void setupService() {
        userArgumentsCaptor = ArgumentCaptor.forClass(User.class);
        userDAO = mock(UserDAOImpl.class);
        serviceUnderTests = new UserServiceImpl((UserDAOImpl) userDAO);
    }

    @Test
    public void getUserByEmail() {
        when(serviceUnderTests.getUserByEmail("email")).thenReturn(new User(1L, "email", "password"));
        User user = serviceUnderTests.getUserByEmail("email");

        Assert.assertEquals((Long) 1L, user.getId());
        Assert.assertEquals("email", user.getEmail());
        Assert.assertEquals("password", user.getPassword());
    }

    @Test
    public void update() {
        User user = new User(1L, "mariana@gmail.com", "123456");
        serviceUnderTests.updateUser(user);

        verify(userDAO)
                .update(userArgumentsCaptor.capture());

        User capturedUpdatedUser = userArgumentsCaptor.getValue();

        Assert.assertEquals(capturedUpdatedUser, user);
    }

    @Test
    public void getById() {
        when(serviceUnderTests.getUserById(1L)).thenReturn(new User(1L, "email", "password"));
        User user = serviceUnderTests.getUserById(1L);

        Assert.assertEquals((Long) 1L, user.getId());
        Assert.assertEquals("email", user.getEmail());
        Assert.assertEquals("password", user.getPassword());
    }
}