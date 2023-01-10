package com.daria.realestate.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long estateId;

    public AppointmentDTO(String email, String firstName, String lastName, String phoneNumber, LocalDateTime start, LocalDateTime end, Long estateId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.start = start;
        this.end = end;
        this.estateId = estateId;
    }

    public String getEmail() {
        return email;
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

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Long getEstateId() {
        return estateId;
    }

    @Override
    public String toString() {
        return "AppointmentDTO{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", estateId=" + estateId +
                '}';
    }
}
