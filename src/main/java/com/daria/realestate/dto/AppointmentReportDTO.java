package com.daria.realestate.dto;

import com.daria.realestate.domain.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentReportDTO {

    private LocalDateTime start;
    private LocalDateTime end;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AppointmentStatus appointmentStatus;

    public AppointmentReportDTO(LocalDateTime start, LocalDateTime end,  String email, String firstName, String lastName, String phoneNumber, AppointmentStatus appointmentStatus) {
        this.start = start;
        this.end = end;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.appointmentStatus = appointmentStatus;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
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

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    @Override
    public String toString() {
        return "AppointmentReportDTO{" +
                "start=" + start +
                ", end=" + end +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", appointmentStatus=" + appointmentStatus +
                '}';
    }
}
