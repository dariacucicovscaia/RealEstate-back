package com.daria.realestate.dto;

import com.daria.realestate.domain.enums.Role;

public class RegistrationDTO {
    private final String email;
    private final String password;

    private final Role role;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;

    private final String fullAddress;
    private final String city;
    private final String country;

    public RegistrationDTO(String email, String password, Role role, String firstName, String lastName, String phoneNumber, String fullAddress, String city, String country) {
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


    public Role getRole() {
        return role;
    }

}
