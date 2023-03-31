package com.daria.realestate.dto;

import com.daria.realestate.domain.enums.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegistrationDTO {
    @Email
    private final String email;
    @NotNull(message="password field cant be null")
    private final String password;
    private final Role role;
    @NotBlank(message="first name field cant be null or a whitespace")
    private final String firstName;
    @NotBlank(message="last name field cant be null or a whitespace")
    private final String lastName;
    @NotBlank(message="phone number field cant be null or a whitespace")
    private final String phoneNumber;
    @NotBlank(message="full address field cant be null or a whitespace")
    private final String fullAddress;
    @NotBlank(message="city field cant be null or a whitespace")
    private final String city;
    @NotBlank(message="country field cant be null or a whitespace")
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
