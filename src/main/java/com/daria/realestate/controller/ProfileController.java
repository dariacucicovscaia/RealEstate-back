package com.daria.realestate.controller;

import com.daria.realestate.domain.Profile;
import com.daria.realestate.service.ProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PutMapping("/addProfilePicture/{userId}")
    public Profile updateProfilePicture(@PathVariable long userId,@RequestParam(value = "profilePicture") String profilePicture) {
        return profileService.updateProfilePicture(userId, profilePicture);
    }
}
