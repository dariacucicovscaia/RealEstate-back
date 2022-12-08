package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.ApartmentType;

public class Apartment extends Imobil {
    private int floorNumber;
    private ApartmentType apartmentType;
    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }
}
