package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.Role;
import com.daria.realestate.dto.Page;
import com.daria.realestate.dto.RegistrationDTO;
import com.daria.realestate.dto.UserWithAllProfileDetails;
import com.daria.realestate.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final ProfileDAO profileDAO;
    private final AddressDAO addressDAO;
    private final UserRoleDAO userRoleDAO;
    private final BCryptPasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserDAO userDAO, ProfileDAO profileDAO, AddressDAO addressDAO, UserRoleDAO userRoleDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.profileDAO = profileDAO;
        this.addressDAO = addressDAO;
        this.userRoleDAO = userRoleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userDAO.getUserByEmail(email);
        user.setRoles(userRoleDAO.getRolesOfAUser(user.getId()));
        return user;
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
    public Page<UserWithAllProfileDetails> getAllUsers(String criteria, PaginationFilter paginationFilter) {
        List<UserWithAllProfileDetails> content = userDAO.getAllUsers(criteria, paginationFilter);

        for (UserWithAllProfileDetails user : content) {
            user.setRoles(userRoleDAO.getRolesOfAUser(user.getId()));
        }

        return new Page<>(content, userDAO.getAllUsersCount(criteria), paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed());
    }

    @Override
    public User getEstateOwner(long estateId) {
        return userDAO.getOwnerOfAnEstate(estateId);
    }

    @Override
    public User updateProfileStatus(long userId, boolean isActive) {
        return userDAO.updateProfileStatus( userId,  isActive);
    }

    @Override
    public RegistrationDTO registerUser(RegistrationDTO registrationDTO) {
        User user = new User(registrationDTO.getEmail(), passwordEncoder.encode(registrationDTO.getPassword()));
        user.setAccountActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user = userDAO.create(user);

        Address address = addressDAO.create(new Address(registrationDTO.getFullAddress(), registrationDTO.getCity(), registrationDTO.getCountry()));
        Profile profile = profileDAO.create(new Profile(registrationDTO.getFirstName(), registrationDTO.getLastName(), registrationDTO.getPhoneNumber(), address, user));
        Role role;

        if (registrationDTO.getRole() == null) {
            role = userRoleDAO.create(user.getId(), Role.USER);
        } else {
            role = userRoleDAO.create(user.getId(), registrationDTO.getRole());
        }

        logger.info("A new user has been created" + user);
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
