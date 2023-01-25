package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.configuration.DataSourceConfig;
import com.daria.realestate.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserDAOImplTest {
    private UserDAO userDAO;

    @Before
    public void init() {
        userDAO = new UserDAOImpl(new DataSourceConfig().dataSource());
    }

    @Test
    public void testGetUserByEmail() {
        String email = "mariana@example.com";
        User extractedUser = userDAO.getUserByEmail(email);

        Assert.assertEquals((Long) 1L, extractedUser.getId());
        Assert.assertEquals(email, extractedUser.getEmail());
        Assert.assertEquals("123456", extractedUser.getPassword());
    }

    @Test
    public void testInsertUser() {
        User userToInsert = new User("userToInsertAndRemoveByEmail@mail.com", "123456qweasd");

        User createdUser = userDAO.create(userToInsert);

        Assert.assertEquals(userToInsert.getEmail(), createdUser.getEmail());
        Assert.assertEquals(userToInsert.getPassword(), createdUser.getPassword());

        userDAO.removeByEmail(createdUser.getEmail());
    }

    @Test
    public void testInsertUserAndRemoveById() {
        User userToInsert = new User("userToInsertAndRemoveById@mail.com", "123456qweasd");

        User createdUser = userDAO.create(userToInsert);

        Assert.assertEquals(userToInsert.getEmail(), createdUser.getEmail());
        Assert.assertEquals(userToInsert.getPassword(), createdUser.getPassword());

        userDAO.removeById(createdUser.getId());
    }

    @Test
    public void testUpdateUser() {
        String newEmail = "updatedEmail@example.com";
        String newPassword = "123456";
        User initialUser = userDAO.getUserByEmail("mariana@example.com");

        User updatedUser = userDAO.update(new User(1L, newEmail, newPassword));

        Assert.assertEquals(newEmail, updatedUser.getEmail());
        Assert.assertEquals(newPassword, updatedUser.getPassword());

        userDAO.update(initialUser);
    }


    @Test
    public void testGetById() {
        long id = 1;
        long idOfTheUserById = userDAO.getById(id).getId();
        Assert.assertEquals(id, idOfTheUserById);
    }
    @Test
    public void getUsersThatHaveAppointmentsTest() {
        List<User> usersThatHaveAppointments = userDAO.getAllUsersThatHaveAppointments();
        Assert.assertNotNull(usersThatHaveAppointments);
    }

    @Test
    public void getOwnerOfAnEstateTest(){
        User user= userDAO.getOwnerOfAnEstate(1L);

        Assert.assertEquals("mariana@example.com", user.getEmail());
        Assert.assertEquals("123456", user.getPassword());
    }
}
