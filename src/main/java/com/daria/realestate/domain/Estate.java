package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;


public class Estate {
    private Long id;
    private PaymentTransactionType paymentTransactionType;
    private EstateStatus acquisitionStatus;

    private Address address;
    private User owner;

    public Estate() {
    }

    public Estate(Long id, String paymentTransactionType, String acquisitionStatus) {
        this.id = id;
        this.paymentTransactionType = PaymentTransactionType.valueOf(paymentTransactionType);
        this.acquisitionStatus = EstateStatus.valueOf(acquisitionStatus);
    }
    public Estate( String paymentTransactionType, String acquisitionStatus) {
        this.paymentTransactionType = PaymentTransactionType.valueOf(paymentTransactionType);
        this.acquisitionStatus = EstateStatus.valueOf(acquisitionStatus);
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
