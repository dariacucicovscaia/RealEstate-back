package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.TypeOfEstate;

import java.time.LocalDate;

public class EstateDetails {
    private int squareMeters;
    private int numberOfRooms;
    private int numberOfBathRooms;
    private int numberOfGarages;
    private LocalDate yearOfConstruction;
    private TypeOfEstate typeOfEstate;
    private Estate estate;

    public EstateDetails() {
        this.estate = new Estate();
    }

    public EstateDetails(int squareMeters, int numberOfRooms, int numberOfBathRooms, int numberOfGarages, LocalDate yearOfConstruction, TypeOfEstate typeOfEstate) {
        this.squareMeters = squareMeters;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathRooms = numberOfBathRooms;
        this.numberOfGarages = numberOfGarages;
        this.yearOfConstruction = yearOfConstruction;
        this.typeOfEstate = typeOfEstate;
    }

    public EstateDetails(int squareMeters, int numberOfRooms, int numberOfBathRooms, int numberOfGarages, LocalDate yearOfConstruction, String typeOfEstate, Long estateId) {
        this.squareMeters = squareMeters;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathRooms = numberOfBathRooms;
        this.numberOfGarages = numberOfGarages;
        this.yearOfConstruction = yearOfConstruction;
        this.typeOfEstate = TypeOfEstate.valueOf(typeOfEstate);
        setEstateId(estateId);
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

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public void setEstateId(long id) {
        this.estate = new Estate();
        this.estate.setId(id);
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


    @Override
    public String toString() {
        return "EstateDetails{" +
                "squareMeters=" + squareMeters +
                ", numberOfRooms=" + numberOfRooms +
                ", numberOfBathRooms=" + numberOfBathRooms +
                ", numberOfGarages=" + numberOfGarages +
                ", yearOfConstruction=" + yearOfConstruction +
                ", typeOfEstate=" + typeOfEstate +
                '}';
    }
}
