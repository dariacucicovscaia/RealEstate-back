package com.daria.realestate.service.impl;

import com.daria.realestate.dao.ProfileDAO;
import com.daria.realestate.domain.Profile;
import com.daria.realestate.service.ProfileService;
import org.jacoco.agent.rt.internal_b6258fc.output.FileOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileDAO profileDAO;
    Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);


    public ProfileServiceImpl(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    @Override
    public Profile updateProfile(long userId, Profile profile) {
        return profileDAO.update(userId, profile);
    }

    @Override
    public Profile getProfileById(Long id) {
        return profileDAO.getById(id);
    }

    @Override
    public Profile updateProfilePicture(long userId, String profilePicture) {
        logger.info(profilePicture);
        return profileDAO.updateProfilePicture(userId, profilePicture);
    }
}
