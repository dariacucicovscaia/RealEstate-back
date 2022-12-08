package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.TypeOfCommercialSpace;

public class Commercial extends Imobil {
    private TypeOfCommercialSpace typeOfCommercialSpace;

    public TypeOfCommercialSpace getTypeOfCommercialSpace() {
        return typeOfCommercialSpace;
    }

    public void setTypeOfCommercialSpace(TypeOfCommercialSpace typeOfCommercialSpace) {
        this.typeOfCommercialSpace = typeOfCommercialSpace;
    }
}
