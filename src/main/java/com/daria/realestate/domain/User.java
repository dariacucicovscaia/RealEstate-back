package com.daria.realestate.domain;


import com.daria.realestate.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class User {
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private List<Appointment> appointment;
    private List<Role> roles = new ArrayList<>();


    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
