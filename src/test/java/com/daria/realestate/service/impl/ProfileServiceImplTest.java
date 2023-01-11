package com.daria.realestate.service.impl;

import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.impl.ProfileDAOImpl;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.service.ProfileService;
import org.junit.*;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class ProfileServiceImplTest {
    @Mock
    private ProfileDAO profileDAO;
    private ProfileService serviceUnderTests;

    @Before
    public void beforeClass() {
        profileDAO = mock(ProfileDAOImpl.class);
        serviceUnderTests = new ProfileServiceImpl((ProfileDAOImpl) profileDAO);
    }
    
    @Test
    public void updateProfile() {
        Profile profile = new Profile("firstName", "lastName", "phoneNumber");
        when(profileDAO.update(profile)).thenReturn(profile);

        serviceUnderTests.updateProfile(profile);

        verify(profileDAO).update(profile);
    }

    @Test
    public void getProfileById() {
        when(profileDAO.getById(1L)).thenReturn(new Profile("firstName", "lastName", "phoneNumber"));
        Profile profile = serviceUnderTests.getProfileById(1L);

        verify(profileDAO).getById(1L);
        Assert.assertNotNull(profile);
    }

}