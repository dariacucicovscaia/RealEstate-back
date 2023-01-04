package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.dao.impl.AddressDAOImpl;
import com.daria.realestate.dao.impl.ProfileDAOImpl;
import com.daria.realestate.dao.impl.UserDAOImpl;
import com.daria.realestate.dao.impl.UserRoleDAOImpl;
import com.daria.realestate.domain.enums.Roles;
import com.daria.realestate.dto.UserDTO;
import com.daria.realestate.service.UserRegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

public class UserRegistrationServiceImplTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private AddressDAO addressDAO;
    @Mock
    private ProfileDAO profileDAO;
    @Mock
    private UserRoleDAO userRoleDAO;
    private ArgumentCaptor<UserDTO> userDTOArgumentCaptor;
    private UserRegistrationService serviceUnderTest;

    @Before
    public void setupService() {
        userDTOArgumentCaptor = ArgumentCaptor.forClass(UserDTO.class);

        userDAO = mock(UserDAOImpl.class);
        userRoleDAO = mock(UserRoleDAOImpl.class);
        addressDAO = mock(AddressDAOImpl.class);
        profileDAO = mock(ProfileDAOImpl.class);

        serviceUnderTest = new UserRegistrationServiceImpl(userDAO, userRoleDAO, addressDAO, profileDAO);
    }

    @Test
    public void registerUser() {
        UserDTO userDTO = new UserDTO("email", "password", Roles.USER, "firstName", "lastName", "phoneNumber", "fullAddress", "city", "country");


        System.out.println(userDTOArgumentCaptor.getValue());
      //  verify(userDAO).create(userDTOArgumentCaptor.capture());
    }
}