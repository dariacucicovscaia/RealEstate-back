package com.daria.realestate.dao;

import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;

import java.util.List;

public interface EstateDAO extends DAO<Estate> {

    /**
     * gets all the Estates by the specified type and paginates them
     *
     * @param paymentTransactionType type that we want to sort by
     * @param paginationFilter       filter for using pagination
     * @return List of all the paginated estates
     */
    List<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType paymentTransactionType, EstateStatus acquisitionStatus, PaginationFilter paginationFilter);

    /**
     * Update estate
     * @param estate to be updated with the new Values set
     * @return
     */
    Estate update(Estate estate);
    //todo add comments
    User getEstateOwner(Estate estate);
    Address getEstateAddress(Estate estate);
}
