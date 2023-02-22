package com.daria.realestate.dto;

import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private final long appointmentId;

    private final LocalDateTime appointmentStart;
    private final LocalDateTime appointmentEnd;

    private final String estatesOwnerEmail;
    private final String usersEmail;

    private final PaymentTransactionType paymentTransactionType;
    private final AcquisitionStatus acquisitionStatus;

    private final String fullEstateAddress;
    private final String estateCity;
    private final String estateCountry;

    public AppointmentDTO(long appointmentId, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, String estatesOwnerEmail, String usersEmail, PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus, String fullEstateAddress, String estateCity, String estateCountry) {
        this.appointmentId = appointmentId;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.estatesOwnerEmail = estatesOwnerEmail;
        this.usersEmail = usersEmail;
        this.paymentTransactionType = paymentTransactionType;
        this.acquisitionStatus = acquisitionStatus;
        this.fullEstateAddress = fullEstateAddress;
        this.estateCity = estateCity;
        this.estateCountry = estateCountry;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }

    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    public String getEstatesOwnerEmail() {
        return estatesOwnerEmail;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public PaymentTransactionType getPaymentTransactionType() {
        return paymentTransactionType;
    }

    public AcquisitionStatus getAcquisitionStatus() {
        return acquisitionStatus;
    }

    public String getFullEstateAddress() {
        return fullEstateAddress;
    }

    public String getEstateCity() {
        return estateCity;
    }

    public String getEstateCountry() {
        return estateCountry;
    }
}
