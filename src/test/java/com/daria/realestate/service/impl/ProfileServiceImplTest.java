package com.daria.realestate.service.impl;

import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.impl.ProfileDAOImpl;
import com.daria.realestate.dao.impl.UserDAOImpl;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.domain.User;
import com.daria.realestate.service.ProfileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProfileServiceImplTest {
    @Mock
    private ProfileDAO profileDAO;

    private ProfileService serviceUnderTests;
    private ArgumentCaptor<Profile> profileArgumentsCaptor;

    @Before
    public void setupService() {
        profileArgumentsCaptor = ArgumentCaptor.forClass(Profile.class);
        profileDAO = mock(ProfileDAOImpl.class);
        serviceUnderTests = new ProfileServiceImpl((ProfileDAOImpl) profileDAO);
    }

    @Test
    public void updateProfile() {
        Profile profile = new Profile("firstName", "lastName", "phoneNumber");
        serviceUnderTests.updateProfile(profile);

        verify(profileDAO)
                .update(profileArgumentsCaptor.capture());

        Profile captureUpdatedProfile = profileArgumentsCaptor.getValue();

        Assert.assertEquals(profile, captureUpdatedProfile);
    }

    @Test
    public void getProfileById() {
        when(serviceUnderTests.getProfileById(1L)).thenReturn(new Profile("firstName", "lastName", "phoneNumber"));
        Profile profile = serviceUnderTests.getProfileById(1L);

        Assert.assertEquals("firstName", profile.getFirstName());
        Assert.assertEquals("lastName", profile.getLastName());
        Assert.assertEquals("phoneNumber", profile.getPhoneNumber());
    }
}