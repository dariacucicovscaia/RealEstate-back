package com.daria.realestate.dto;

import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;

import java.util.List;

public class EstateSearchFilter {
    private final AcquisitionStatus acquisitionStatus;
    private final PaymentTransactionType paymentTransactionType;
    private final int squareMetersFrom;
    private final int squareMetersTo;
    private final int numberOfRoomsFrom;
    private final int numberOfRoomsTo;
    private final int numberOfBathroomsFrom;
    private final int numberOfBathroomsTo;
    private final int numberOfGaragesFrom;
    private final int numberOfGaragesTo;
    private final String yearOfConstructionFrom;
    private final String yearOfConstructionTo;
    private List<TypeOfEstate> typeOfEstates;
    private final long priceFrom;
    private final long priceTo;
    private final String city;
    private final String country;
    private boolean latestAdded;


    public EstateSearchFilter(AcquisitionStatus acquisitionStatus, PaymentTransactionType paymentTransactionType, int squareMetersFrom, int squareMetersTo, int numberOfRoomsFrom, int numberOfRoomsTo, int numberOfBathroomsFrom, int numberOfBathroomsTo, int numberOfGaragesFrom, int numberOfGaragesTo, String yearOfConstructionFrom, String yearOfConstructionTo,  List<TypeOfEstate> typeOfEstates, long priceFrom, long priceTo, String city, String country) {
        this.acquisitionStatus = acquisitionStatus;
        this.paymentTransactionType = paymentTransactionType;
        this.squareMetersFrom = squareMetersFrom;
        this.squareMetersTo = squareMetersTo;
        this.numberOfRoomsFrom = numberOfRoomsFrom;
        this.numberOfRoomsTo = numberOfRoomsTo;
        this.numberOfBathroomsFrom = numberOfBathroomsFrom;
        this.numberOfBathroomsTo = numberOfBathroomsTo;
        this.numberOfGaragesFrom = numberOfGaragesFrom;
        this.numberOfGaragesTo = numberOfGaragesTo;
        this.yearOfConstructionFrom = yearOfConstructionFrom;
        this.yearOfConstructionTo = yearOfConstructionTo;
        this.typeOfEstates = typeOfEstates;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.city = city;
        this.country = country;
    }

    public AcquisitionStatus getAcquisitionStatus() {
        return acquisitionStatus;
    }


    public PaymentTransactionType getPaymentTransactionType() {
        return paymentTransactionType;
    }


    public int getSquareMetersFrom() {
        return squareMetersFrom;
    }


    public int getSquareMetersTo() {
        return squareMetersTo;
    }


    public int getNumberOfRoomsFrom() {
        return numberOfRoomsFrom;
    }


    public int getNumberOfRoomsTo() {
        return numberOfRoomsTo;
    }


    public int getNumberOfBathroomsFrom() {
        return numberOfBathroomsFrom;
    }


    public int getNumberOfBathroomsTo() {
        return numberOfBathroomsTo;
    }


    public int getNumberOfGaragesFrom() {
        return numberOfGaragesFrom;
    }


    public int getNumberOfGaragesTo() {
        return numberOfGaragesTo;
    }


    public String getYearOfConstructionFrom() {
        return yearOfConstructionFrom;
    }


    public String getYearOfConstructionTo() {
        return yearOfConstructionTo;
    }


    public List<TypeOfEstate> getTypeOfEstates() {
        return typeOfEstates;
    }

    public long getPriceFrom() {
        return priceFrom;
    }


    public long getPriceTo() {
        return priceTo;
    }


    public String getCity() {
        return city;
    }


    public String getCountry() {
        return country;
    }

    public void setTypeOfEstates(List<TypeOfEstate> typeOfEstates) {
        this.typeOfEstates = typeOfEstates;
    }

    public boolean getLatestAdded() {
        return latestAdded;
    }

    public void setLatestAdded(boolean latestAdded) {
        this.latestAdded = latestAdded;
    }
}
