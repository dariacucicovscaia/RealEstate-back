package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.PaymentType;

public abstract class Property {
    private Long id;
    private PaymentType paymentType;
    private int squareMeters;
    private Address address;
    private long priceRange;
    private User owner;

    private String status;

    public Property() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public int getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(long priceRange) {
        this.priceRange = priceRange;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
