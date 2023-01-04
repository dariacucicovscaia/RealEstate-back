package com.daria.realestate.service.impl;

import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.dao.impl.ProfileDAOImpl;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.service.ProfileService;

public class ProfileServiceImpl implements ProfileService {
    private final ProfileDAO profileDAO;

    public ProfileServiceImpl(ProfileDAOImpl profileDAO) {
        this.profileDAO = profileDAO;
    }

    @Override
    public Profile updateProfile(Profile profile) {
        return profileDAO.update(profile);
    }

    public Profile getProfileById(Long id){
        return profileDAO.getById(id);
    }
}
