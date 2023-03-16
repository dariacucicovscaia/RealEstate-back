package com.daria.realestate.service;

import com.daria.realestate.domain.Profile;

public interface ProfileService {

    Profile updateProfile(Profile profile);
    Profile getProfileById(Long id);

    Profile updateProfilePicture(long userId, String profilePicture);
}
