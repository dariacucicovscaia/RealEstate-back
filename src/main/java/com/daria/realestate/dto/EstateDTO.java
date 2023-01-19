package com.daria.realestate.dto;

import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EstateDTO {
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

    private Long price;
    private LocalDateTime lastPriceUpdatedAt;
    private String currency;

    public EstateDTO(PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, int squareMeters, int numberOfRooms, int numberOfBathRooms, int numberOfGarages, LocalDate yearOfConstruction, TypeOfEstate typeOfEstate, String fullAddress, String city, String country, String email, Long price, LocalDateTime lastPriceUpdatedAt, String currency) {
        this.paymentTransactionType = paymentTransactionType;
        this.acquisitionStatus = acquisitionStatus;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.squareMeters = squareMeters;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathRooms = numberOfBathRooms;
        this.numberOfGarages = numberOfGarages;
        this.yearOfConstruction = yearOfConstruction;
        this.typeOfEstate = typeOfEstate;
        this.fullAddress = fullAddress;
        this.city = city;
        this.country = country;
        this.email = email;
        this.price = price;
        this.lastPriceUpdatedAt = lastPriceUpdatedAt;
        this.currency = currency;
    }

    public PaymentTransactionType getPaymentTransactionType() {
        return paymentTransactionType;
    }


    public AcquisitionStatus getAcquisitionStatus() {
        return acquisitionStatus;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }


    public int getSquareMeters() {
        return squareMeters;
    }


    public int getNumberOfRooms() {
        return numberOfRooms;
    }


    public int getNumberOfBathRooms() {
        return numberOfBathRooms;
    }


    public int getNumberOfGarages() {
        return numberOfGarages;
    }


    public LocalDate getYearOfConstruction() {
        return yearOfConstruction;
    }


    public TypeOfEstate getTypeOfEstate() {
        return typeOfEstate;
    }


    public String getFullAddress() {
        return fullAddress;
    }


    public String getCity() {
        return city;
    }


    public String getCountry() {
        return country;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Long getPrice() {
        return price;
    }


    public LocalDateTime getLastPriceUpdatedAt() {
        return lastPriceUpdatedAt;
    }


    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "EstateDTO{" +
                "paymentTransactionType=" + paymentTransactionType +
                ", acquisitionStatus=" + acquisitionStatus +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", squareMeters=" + squareMeters +
                ", numberOfRooms=" + numberOfRooms +
                ", numberOfBathRooms=" + numberOfBathRooms +
                ", numberOfGarages=" + numberOfGarages +
                ", yearOfConstruction=" + yearOfConstruction +
                ", typeOfEstate=" + typeOfEstate +
                ", fullAddress='" + fullAddress + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", price=" + price +
                ", lastPriceUpdatedAt=" + lastPriceUpdatedAt +
                ", currency='" + currency + '\'' +
                '}';
    }
}
