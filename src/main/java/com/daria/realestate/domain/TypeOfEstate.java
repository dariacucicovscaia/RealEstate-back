package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.TypesOfEstates;

public class TypeOfEstate {
    private Long id;
    private TypesOfEstates typeOfEstate;

    public TypeOfEstate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypesOfEstates getTypeOfEstate() {
        return typeOfEstate;
    }

    public void setTypeOfEstate(TypesOfEstates typeOfEstate) {
        this.typeOfEstate = typeOfEstate;
    }
}
