package com.daria.realestate.controller;

import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.dto.Page;
import com.daria.realestate.dto.RegistrationDTO;
import com.daria.realestate.dto.UserWithAllProfileDetails;
import com.daria.realestate.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public RegistrationDTO register(@RequestBody RegistrationDTO userDTO) {
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

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/changeAccountStatus/{userId}/{accountStatus}")
    public User changeAccountStatus(@PathVariable long userId, @PathVariable boolean accountStatus) {
        return userService.updateProfileStatus(userId, accountStatus);
    }


    @GetMapping("/getEstateOwner/{estateId}")
    public User getEstateOwner(@PathVariable long estateId) {
        return userService.getEstateOwner(estateId);
    }
}

