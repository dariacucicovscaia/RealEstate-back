package com.daria.realestate.dao;

import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.EstateDetails;
import com.daria.realestate.domain.PaginationFilter;

import java.time.LocalDate;
import java.util.List;

public interface EstateDetailsDAO extends DAO<EstateDetails> {

    /**
     * Filters estate by all parameters
     *
     * @param squareMeters
     * @param numberOfRooms
     * @param numberOfBathRooms
     * @param numberOfGarages
     * @param yearOfConstruction
     * @param typeOfEstate
     * @param paginationFilter
     * @return list of estates that match the params
     */
    List<EstateDetails> getFilteredEstateDetailsByAllParameters(int squareMeters, int numberOfRooms, int numberOfBathRooms, int numberOfGarages, LocalDate yearOfConstruction, String typeOfEstate, PaginationFilter paginationFilter);


}
