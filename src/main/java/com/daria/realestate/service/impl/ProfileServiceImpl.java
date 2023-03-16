package com.daria.realestate.service.impl;

import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.service.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileDAO profileDAO;

    public ProfileServiceImpl(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    @Override
    public Profile updateProfile(Profile profile) {
        return profileDAO.update(profile);
    }
    @Override
    public Profile getProfileById(Long id){
        return profileDAO.getById(id);
    }

    @Override
    public Profile updateProfilePicture(long userId, String profilePicture) {
        return profileDAO.updateProfilePicture(userId, profilePicture);
    }
}
