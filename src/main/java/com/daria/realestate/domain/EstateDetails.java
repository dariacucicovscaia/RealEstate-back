package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.TypeOfEstate;

import java.time.LocalDate;
import java.util.Arrays;

public class EstateDetails {
    private int squareMeters;
    private int numberOfRooms;
    private int numberOfBathRooms;
    private int[] floorNumbers;
    private int numberOfGarages;
    private LocalDate yearOfConstruction;
    private TypeOfEstate typeOfEstate;

    private Estate estate;

    public EstateDetails() {
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

    public int[] getFloorNumbers() {
        return floorNumbers;
    }

    public void setFloorNumbers(int[] floorNumbers) {
        this.floorNumbers = floorNumbers;
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

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    @Override
    public String toString() {
        return "EstateDetails{" +
                "squareMeters=" + squareMeters +
                ", numberOfRooms=" + numberOfRooms +
                ", numberOfBathRooms=" + numberOfBathRooms +
                ", floorNumbers=" + Arrays.toString(floorNumbers) +
                ", numberOfGarages=" + numberOfGarages +
                ", yearOfConstruction=" + yearOfConstruction +
                ", typeOfEstate=" + typeOfEstate +
                ", estate=" + estate +
                '}';
    }
}
