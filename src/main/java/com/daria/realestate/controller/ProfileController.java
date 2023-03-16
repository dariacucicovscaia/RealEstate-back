package com.daria.realestate.controller;

import com.daria.realestate.domain.Profile;
import com.daria.realestate.service.ProfileService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    public Profile updateProfilePicture(@PathVariable long userId, @PathVariable String profilePicture){
        return profileService.updateProfilePicture(userId, profilePicture);
    }
}
