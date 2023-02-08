package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;

import java.time.LocalDateTime;


public class Estate {
    private Long id;
    private PaymentTransactionType paymentTransactionType;
    private AcquisitionStatus acquisitionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    private Address address;
    private User owner;


    public Estate() {
    }

    public Estate(Long id, String paymentTransactionType, String acquisitionStatus, LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.paymentTransactionType = PaymentTransactionType.valueOf(paymentTransactionType);
        this.acquisitionStatus = AcquisitionStatus.valueOf(acquisitionStatus);
        this.createdAt =createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Estate (PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, Address address, User owner) {
        this.paymentTransactionType = paymentTransactionType;
        this.acquisitionStatus = acquisitionStatus;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.address = address;
        this.owner = owner;
    }

    public Estate(String paymentTransactionType, String acquisitionStatus, LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.paymentTransactionType = PaymentTransactionType.valueOf(paymentTransactionType);
        this.acquisitionStatus = AcquisitionStatus.valueOf(acquisitionStatus);
        this.createdAt =createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }
    public Estate (long id, String paymentTransactionType, String acquisitionStatus, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, Address address, User owner) {
        this.id = id;
        this.paymentTransactionType = PaymentTransactionType.valueOf(paymentTransactionType);
        this.acquisitionStatus = AcquisitionStatus.valueOf(acquisitionStatus);
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.address = address;
        this.owner = owner;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public AcquisitionStatus getAcquisitionStatus() {
        return acquisitionStatus;
    }

    public void setAcquisitionStatus(AcquisitionStatus acquisitionStatus) {
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
