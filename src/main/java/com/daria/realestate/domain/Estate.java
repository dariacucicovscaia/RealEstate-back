package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;

import java.time.LocalDate;
import java.util.Map;

public class Estate {
    private Long id;
    private int squareMeters;
    private Address address;
    private Price price;
    private User owner;
    private int numberOfRooms;
    private int numberOfBathRooms;

    /**
     * if it's an apartment the value of the map needs to be specified
     * if it's a commercial space the same
     * if it's a house - not mandatory
     */
    private Map<Integer, Integer> totalFloorsAndFloorNumber;
    private int numberOfGarages;
    private LocalDate yearOfConstruction;
    private PaymentTransactionType paymentTransactionType;
    private EstateStatus acquisitionStatus;
    private TypeOfEstate typeOfEstate;

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

    public Map<Integer, Integer> getTotalFloorsAndFloorNumber() {
        return totalFloorsAndFloorNumber;
    }

    public void setTotalFloorsAndFloorNumber(Map<Integer, Integer> totalFloorsAndFloorNumber) {
        this.totalFloorsAndFloorNumber = totalFloorsAndFloorNumber;
    }
}
