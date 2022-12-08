package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.HouseType;

public class House extends Estate {
    private HouseType houseType;

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }
}
