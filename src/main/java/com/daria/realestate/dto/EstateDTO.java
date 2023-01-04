package com.daria.realestate.dto;

import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EstateDTO  {
    private PaymentTransactionType paymentTransactionType;
    private AcquisitionStatus acquisitionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    private int squareMeters;
    private int numberOfRooms;
    private int numberOfBathRooms;
    private int numberOfGarages;
    private LocalDate yearOfConstruction;
    private TypeOfEstate typeOfEstate;

    private String fullAddress;
    private String city;
    private String country;

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private Long price;
    private LocalDateTime lastPriceUpdatedAt;
    private String currency;



    public PaymentTransactionType getPaymentTransactionType() {
        return paymentTransactionType;
    }

    public void setPaymentTransactionType(PaymentTransactionType paymentTransactionType) {
        this.paymentTransactionType = paymentTransactionType;
    }

    public AcquisitionStatus getAcquisitionStatus() {
        return acquisitionStatus;
    }

    public void setAcquisitionStatus(AcquisitionStatus acquisitionStatus) {
        this.acquisitionStatus = acquisitionStatus;
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

    public int getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfBathRooms() {
        return numberOfBathRooms;
    }

    public void setNumberOfBathRooms(int numberOfBathRooms) {
        this.numberOfBathRooms = numberOfBathRooms;
    }

    public int getNumberOfGarages() {
        return numberOfGarages;
    }

    public void setNumberOfGarages(int numberOfGarages) {
        this.numberOfGarages = numberOfGarages;
    }

    public LocalDate getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(LocalDate yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public TypeOfEstate getTypeOfEstate() {
        return typeOfEstate;
    }

    public void setTypeOfEstate(TypeOfEstate typeOfEstate) {
        this.typeOfEstate = typeOfEstate;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public LocalDateTime getLastPriceUpdatedAt() {
        return lastPriceUpdatedAt;
    }

    public void setLastPriceUpdatedAt(LocalDateTime lastPriceUpdatedAt) {
        this.lastPriceUpdatedAt = lastPriceUpdatedAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
