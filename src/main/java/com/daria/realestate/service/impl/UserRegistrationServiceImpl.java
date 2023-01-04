package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.UserRole;
import com.daria.realestate.dto.UserDTO;
import com.daria.realestate.service.UserRegistrationService;

public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;
    private final AddressDAO addressDAO;
    private final ProfileDAO profileDAO;

    public UserRegistrationServiceImpl(UserDAO userDAO, UserRoleDAO userRoleDAO, AddressDAO addressDAO, ProfileDAO profileDAO) {
        this.userDAO = userDAO;
        this.userRoleDAO = userRoleDAO;
        this.addressDAO = addressDAO;
        this.profileDAO = profileDAO;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = new User(userDTO.getEmail(), userDTO.getPassword());
        Address address = new Address(userDTO.getFullAddress(), userDTO.getCity(), userDTO.getCountry());
        Profile profile = new Profile(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber(), address, user);
        UserRole userRole = new UserRole(user, userDTO.getRole());

        user = userDAO.create(user);
        userRole = userRoleDAO.create(userRole);
        address = addressDAO.create(address);
        profile = profileDAO.create(profile);


        return userDTO;
    }
}
