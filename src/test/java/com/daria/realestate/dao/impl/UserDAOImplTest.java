package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.domain.enums.OrderBy;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserDAOImplTest {
    private UserDAO<User> userDAO;

    @Before
    public void init() {
        userDAO = new UserDAOImpl(DataBaseConnection.getInstance());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserByIdExpectNullPointerException() {
        long id = 0;
        userDAO.getById(id);
    }

    @Test
    public void testGetUserByIdExpectUserFound() {
        User user = userDAO.getById(1);
        Assert.assertNotNull(user);
        Assert.assertEquals("mariana@example.com", user.getEmail());
        Assert.assertEquals("123456qweasd", user.getPassword());
    }

    @Test
    public void testGetAllUsers() {
        Assert.assertNotNull(userDAO.getAll());
    }

    @Test
    public void testInsertUser() {
        User userToInsert = new User("userToInsert@mail.com", "123456qweasd", LocalDateTime.now());

        long createdUserId = userDAO.create(userToInsert);

        User createdUser = userDAO.getById(createdUserId);
        Assert.assertEquals(userToInsert.getEmail(), createdUser.getEmail());
        Assert.assertEquals(userToInsert.getPassword(), createdUser.getPassword());

        userDAO.removeById(createdUserId);
    }

    @Test
    public void testUpdateUser() {
        String newEmail = "updatedEmail@example.com";
        String newPassword = "123456";
        User initialUser = userDAO.getById(1);

        User updatedUser = userDAO.update(new User(1L, newEmail, newPassword, initialUser.getCreatedAt()), 1);

        Assert.assertEquals(newEmail, updatedUser.getEmail());
        Assert.assertEquals(newPassword, updatedUser.getPassword());

        userDAO.update(initialUser, 1);
    }

    @Test
    public void testPageableUsersFetchByASCOrder() {
        String sqlStart = "select * from realestate.user ";
        List<User> users = userDAO.paginateGivenQuery(sqlStart, new PaginationFilter(1, 5, "id", OrderBy.ASC));

        Assert.assertTrue(users.get(0).getId() < users.get(1).getId());
        Assert.assertTrue(users.size() <= 5);
    }

    @Test
    public void testPageableUsersFetchByDESCOrder() {
        String sqlStart = "select * from realestate.user ";
        List<User> users = userDAO.paginateGivenQuery(sqlStart, new PaginationFilter(1, 5, "id", OrderBy.DESC));

        Assert.assertTrue(users.get(0).getId() > users.get(1).getId());
        Assert.assertTrue(users.size() <= 6);
    }

    @Test
    public void testGetUsersCreatedBetweenTwoTimeSlots() {
        LocalDateTime timeFrom = LocalDateTime.of(2019, 12, 24, 14, 33, 48, 123456789);
        LocalDateTime timeTo = LocalDateTime.of(2021, 04, 24, 14, 33, 48, 123456789);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<User> users = userDAO.getUsersCreatedBetweenTwoTimeSlots(timeFrom.format(formatter),
                                                             timeTo.format(formatter),
                                                             new PaginationFilter(1, 5, "id", OrderBy.ASC));
        for (User user : users) {
            Assert.assertTrue(user.getCreatedAt().isAfter(timeFrom) && user.getCreatedAt().isBefore(timeTo));
        }
        Assert.assertTrue(users.size() <= 5);
        Assert.assertTrue(users.get(0).getId() < users.get(1).getId() );
    }

}
