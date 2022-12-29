package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.Roles;

public class UserRole {
    private long id;
    private User user;
    private Roles role;

    public UserRole(long id, String role) {
        this.id = id;
        this.role = Roles.valueOf(role);
    }


    public UserRole(User user, Roles role) {
        this.user = user;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
