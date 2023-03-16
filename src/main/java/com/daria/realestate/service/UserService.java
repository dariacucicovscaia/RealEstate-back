package com.daria.realestate.service;

import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.dto.Page;
import com.daria.realestate.dto.RegistrationDTO;
import com.daria.realestate.dto.UserWithAllProfileDetails;

public interface UserService {
    RegistrationDTO registerUser(RegistrationDTO registrationDTO);
    User getUserByEmail(String email);
    User updateUser(User user);
    User getUserById(Long id);

    Page<UserWithAllProfileDetails> getAllUsers(String criteria , PaginationFilter paginationFilter);

    User getEstateOwner(long estateId);

    User updateProfileStatus(long userId, boolean isActive);
}
