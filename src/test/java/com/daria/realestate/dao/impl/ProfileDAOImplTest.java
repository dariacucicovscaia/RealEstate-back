package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dbconnection.DBConfig;
import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ProfileDAOImplTest {
    private ProfileDAO profileDAO;

    @Before
    public void init() {
        this.profileDAO = new ProfileDAOImpl(new DBConfig().dataSource());}

    @Test
    public void testCreationOfProfile() {
        Address address = new Address();
        address.setId(1L);

        User user = new User();
        user.setId(1L);

        Profile profile = new Profile("anna", "karenina", "+37364458985", address, user);
        Profile createdProfile = profileDAO.create(profile);

        Assert.assertEquals(profile.getFirstName(), createdProfile.getFirstName());
        Assert.assertEquals(profile.getLastName(), createdProfile.getLastName());
        Assert.assertEquals(profile.getPhoneNumber(), createdProfile.getPhoneNumber());

        int removedProfileId = profileDAO.removeById(createdProfile.getId());
        Assert.assertEquals(removedProfileId,1);
    }

    @Test
    public void testGetProfileById() {
        Profile profile = profileDAO.getById(1L);

        Assert.assertEquals((Long) 1L, profile.getId());
        Assert.assertEquals("mariana", profile.getFirstName());
        Assert.assertEquals("smith", profile.getLastName());
        Assert.assertEquals("+1354788541", profile.getPhoneNumber());
    }

    @Test
    public void testUpdateProfile() {
        Profile profile = profileDAO.getById(1L);
        String oldFirstName = profile.getFirstName();

        String newFirstName = "firstName";
        profile.setFirstName(newFirstName);

        profile = profileDAO.update(profile);
        Assert.assertEquals(newFirstName, profile.getFirstName());

        profile.setFirstName(oldFirstName);
        profile = profileDAO.update(profile);
        Assert.assertEquals(oldFirstName, profile.getFirstName());

    }


}
