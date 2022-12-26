package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;

import java.time.LocalDate;


public class Estate {
    private Long id;
    private PaymentTransactionType paymentTransactionType;
    private EstateStatus acquisitionStatus;
    private LocalDate createdAt;
    private LocalDate lastUpdatedAt;

    private Address address;
    private User owner;


    public Estate() {
    }

    public Estate(Long id, String paymentTransactionType, String acquisitionStatus, LocalDate createdAt, LocalDate lastUpdatedAt) {
        this.id = id;
        this.paymentTransactionType = PaymentTransactionType.valueOf(paymentTransactionType);
        this.acquisitionStatus = EstateStatus.valueOf(acquisitionStatus);
        this.createdAt =createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }
    public Estate( String paymentTransactionType, String acquisitionStatus, LocalDate createdAt,LocalDate lastUpdatedAt) {
        this.paymentTransactionType = PaymentTransactionType.valueOf(paymentTransactionType);
        this.acquisitionStatus = EstateStatus.valueOf(acquisitionStatus);
        this.createdAt =createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentTransactionType getPaymentTransactionType() {
        return paymentTransactionType;
    }

    public void setPaymentTransactionType(PaymentTransactionType paymentTransactionType) {
        this.paymentTransactionType = paymentTransactionType;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDate lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public EstateStatus getAcquisitionStatus() {
        return acquisitionStatus;
    }

    public void setAcquisitionStatus(EstateStatus acquisitionStatus) {
        this.acquisitionStatus = acquisitionStatus;
    }

    @Override
    public String toString() {
        return "Estate{" +
                "id=" + id +
                ", paymentTransactionType=" + paymentTransactionType +
                ", acquisitionStatus=" + acquisitionStatus +
                '}';
    }
}
