package com.daria.realestate.dao;

import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;

import java.util.List;

public interface EstateDAO extends AbstractDAO<Estate> {

    /**
     * gets all the Estates by the specified type and paginates them
     *
     * @param paymentTransactionType type that we want to sort by
     * @param paginationFilter       filter for using pagination
     * @return List of all the paginated estates
     */
    List<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType paymentTransactionType, EstateStatus acquisitionStatus, PaginationFilter paginationFilter);

     Estate updateEstateAcquisitionStatus(long id, EstateStatus acquisitionStatus);

    /**
     * Creates an estate
     *
     * @param estate that we want to create
     * @return created estate with the generated id
     */
    Estate createEstate(Estate estate);


    Estate getEstateById(long id);

    long removeEstateById(long id);
}
