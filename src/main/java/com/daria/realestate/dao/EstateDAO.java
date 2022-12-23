package com.daria.realestate.dao;

import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.PaymentTransactionType;

import java.util.List;

public interface EstateDAO<Estate> extends GenericDAO<Estate> {

    /**
     * gets all the Estates by the specified type and paginates them
     *
     * @param paymentTransactionType type that we want to sort by
     * @param paginationFilter       filter for using pagination
     * @return List of all the paginated estates
     */
    List<com.daria.realestate.domain.Estate> getAllEstatesFilteredByPaymentTransactionType(PaymentTransactionType paymentTransactionType, PaginationFilter paginationFilter);
}
