package com.daria.realestate.service;

import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.domain.User;
import com.daria.realestate.dto.Page;
import com.daria.realestate.dto.RegistrationDTO;
import com.daria.realestate.dto.UserProfileDTO;
import com.daria.realestate.dto.UserWithAllProfileDetails;

public interface UserService {
    RegistrationDTO registerUser(RegistrationDTO registrationDTO);
    User getUserByEmail(String email);
    UserWithAllProfileDetails updateUser(long userId, UserProfileDTO userProfileDTO);
    User getUserById(Long id);

    Page<UserWithAllProfileDetails> getAllUsers(String criteria , PaginationFilter paginationFilter);

    User getEstateOwner(long estateId);

    User updateProfileStatus(long userId, boolean isActive);

    UserWithAllProfileDetails getUserWithAllProfileDetails(long userId);


}
