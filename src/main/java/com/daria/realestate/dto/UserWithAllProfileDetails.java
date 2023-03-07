package com.daria.realestate.dto;

import com.daria.realestate.domain.enums.AccountStatus;
import com.daria.realestate.domain.enums.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserWithAllProfileDetails {
    private long id;
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;

    public UserWithAllProfileDetails(long id, String profilePicture, String firstName, String lastName, String email, List<Role> roles, AccountStatus accountStatus, LocalDateTime createdAt) {
        this.id = id;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.accountStatus = accountStatus;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
