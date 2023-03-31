package com.daria.realestate.controller;

import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.dto.Page;
import com.daria.realestate.dto.RegistrationDTO;
import com.daria.realestate.dto.UserProfileDTO;
import com.daria.realestate.dto.UserWithAllProfileDetails;
import com.daria.realestate.service.ProfileService;
import com.daria.realestate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
//TODO use controller adviser for handling validation
    @PostMapping("/register")
    public RegistrationDTO register(@Valid @RequestBody RegistrationDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @GetMapping("/email/{userEmail}")
    public User getUserByEmail(@PathVariable String userEmail) {
        return userService.getUserByEmail(userEmail);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/allUsers")
    public Page<UserWithAllProfileDetails> getAllUsers(@RequestParam(value = "criteria", required = false) String criteria, @RequestParam("page") int page, @RequestParam("size") int size) {
        return userService.getAllUsers(criteria, new PaginationFilter(page, size));
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/user/{userId}")
    public UserWithAllProfileDetails getUsersDetails( @PathVariable long userId) {
        return userService.getUserWithAllProfileDetails(userId);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/changeAccountStatus/{userId}/{accountStatus}")
    public User changeAccountStatus(@PathVariable long userId, @PathVariable boolean accountStatus) {
        return userService.updateProfileStatus(userId, accountStatus);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PutMapping("/updateProfile/{userId}")
    public ResponseEntity<UserWithAllProfileDetails> updateProfile(@PathVariable long userId,
//                                                                   @Valid
                                                                   @RequestBody UserProfileDTO profile) {
        return ResponseEntity.ok(userService.updateUser(userId, profile));
    }


    @GetMapping("/getEstateOwner/{estateId}")
    public User getEstateOwner(@PathVariable long estateId) {
        return userService.getEstateOwner(estateId);
    }
}

