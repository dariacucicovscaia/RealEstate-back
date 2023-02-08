package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.Role;
import com.daria.realestate.dto.RegistrationDTO;
import com.daria.realestate.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final ProfileDAO profileDAO;
    private final AddressDAO addressDAO;
    private final UserRoleDAO userRoleDAO;


    public UserServiceImpl(UserDAO userDAO, ProfileDAO profileDAO, AddressDAO addressDAO, UserRoleDAO userRoleDAO) {
        this.userDAO = userDAO;
        this.profileDAO = profileDAO;
        this.addressDAO = addressDAO;
        this.userRoleDAO = userRoleDAO;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        return userDAO.update(user);
    }

    @Override
    public User getUserById(Long id) {
        User user = userDAO.getById(id);
        user.setRoles(userRoleDAO.getRolesOfAUser(id));
        return user;
    }

    @Override
    public RegistrationDTO registerUser(RegistrationDTO registrationDTO) {
        User user = userDAO.create(new User(registrationDTO.getEmail(), registrationDTO.getPassword()));
        Address address = addressDAO.create(new Address(registrationDTO.getFullAddress(), registrationDTO.getCity(), registrationDTO.getCountry()));
        Profile profile = profileDAO.create(new Profile(registrationDTO.getFirstName(), registrationDTO.getLastName(), registrationDTO.getPhoneNumber(), address, user));
        Role role = userRoleDAO.create(user.getId(), registrationDTO.getRole());

        return new RegistrationDTO(
                user.getEmail(),
                user.getPassword(),
                role,
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhoneNumber(),
                address.getFullAddress(),
                address.getCity(),
                address.getCountry()
        );
    }

}
