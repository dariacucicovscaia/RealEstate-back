package com.daria.realestate.dto;

import com.daria.realestate.domain.enums.Roles;

public class RegistrationDTO {
    private String email;
    private String password;

    private Roles role;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String fullAddress;
    private String city;
    private String country;

    public RegistrationDTO(String email, String password, Roles role, String firstName, String lastName, String phoneNumber, String fullAddress, String city, String country) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.fullAddress = fullAddress;
        this.city = city;
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getFullAddress() {
        return fullAddress;
    }


    public String getCity() {
        return city;
    }


    public String getCountry() {
        return country;
    }


    public Roles getRole() {
        return role;
    }

}
