package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.dao.impl.AddressDAOImpl;
import com.daria.realestate.dao.impl.ProfileDAOImpl;
import com.daria.realestate.dao.impl.UserDAOImpl;
import com.daria.realestate.dao.impl.UserRoleDAOImpl;
import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.Role;
import com.daria.realestate.dto.RegistrationDTO;
import com.daria.realestate.service.RegistrationService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserDAO userDAO;
    private final ProfileDAO profileDAO;
    private final AddressDAO addressDAO;
    private final UserRoleDAO userRoleDAO;

    public RegistrationServiceImpl(UserDAO userDAO, ProfileDAO profileDAO, AddressDAO addressDAO, UserRoleDAO userRoleDAO) {
        this.userDAO = userDAO;
        this.profileDAO = profileDAO;
        this.addressDAO = addressDAO;
        this.userRoleDAO = userRoleDAO;
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
