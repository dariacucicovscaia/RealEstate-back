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
}
