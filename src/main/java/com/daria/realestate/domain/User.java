package com.daria.realestate.domain;


import com.daria.realestate.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    //TODO change account status to boolean
    private boolean isAccountActive;
    private LocalDateTime createdAt;

    private List<Appointment> appointment;
    private List<Role> roles = new ArrayList<>();


    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(long id, String email, boolean isAccountActive, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.isAccountActive = isAccountActive;
        this.createdAt = createdAt;
    }

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountActive() {
        return isAccountActive;
    }

    public void setAccountActive(boolean accountActive) {
        isAccountActive = accountActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
