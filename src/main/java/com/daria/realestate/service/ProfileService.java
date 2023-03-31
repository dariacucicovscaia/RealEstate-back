package com.daria.realestate.service;

import com.daria.realestate.domain.Profile;


public interface ProfileService {

    Profile updateProfile(long userId, Profile profile);
    Profile getProfileById(Long id);

    Profile updateProfilePicture(long userId, String profilePicture) ;
}
