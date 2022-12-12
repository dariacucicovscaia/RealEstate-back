package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;


public class Estate {
    private Long id;
    private Address address;
    private Price price;
    private User owner;
    private PaymentTransactionType paymentTransactionType;
    private EstateStatus acquisitionStatus;
    private EstateDetails estateDetails;

    public Estate() {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public EstateStatus getAcquisitionStatus() {
        return acquisitionStatus;
    }

    public void setAcquisitionStatus(EstateStatus acquisitionStatus) {
        this.acquisitionStatus = acquisitionStatus;
    }

    public EstateDetails getEstateDetails() {
        return estateDetails;
    }

    public void setEstateDetails(EstateDetails estateDetails) {
        this.estateDetails = estateDetails;
    }
}
