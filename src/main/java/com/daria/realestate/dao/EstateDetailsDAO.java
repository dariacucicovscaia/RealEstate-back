package com.daria.realestate.dao;

import com.daria.realestate.domain.EstateDetails;
import com.daria.realestate.domain.PaginationFilter;

import java.time.LocalDate;
import java.util.List;

public interface EstateDetailsDAO extends AbstractDAO<EstateDetails> {
    /**
     * Creates an entity EstateDetails in the database
     *
     * @param estateDetails entity we want to create
     * @return entity with the generated id
     */
    EstateDetails createEstateDetails(EstateDetails estateDetails);

    /**
     * @param squareMeters
     * @param numberOfRooms
     * @param numberOfBathRooms
     * @param numberOfGarages
     * @param yearOfConstruction
     * @param typeOfEstate
     * @param paginationFilter
     * @return
     */
    List<EstateDetails> getFilteredEstateDetailsByAllParameters(int squareMeters, int numberOfRooms, int numberOfBathRooms, int numberOfGarages, LocalDate yearOfConstruction, String typeOfEstate, PaginationFilter paginationFilter);

    long removeEstateDetailsById(long id);
}
