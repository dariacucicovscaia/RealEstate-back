package com.daria.realestate.dao;

import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.dto.EstateSearchFilter;

import java.util.List;

public interface EstateDAO extends DAO<Estate> {

    /**
     * gets all the Estates by the specified type and paginates them
     *
     * @param paymentTransactionType type that we want to sort by
     * @param paginationFilter       filter for using pagination
     * @return List of all the paginated estates
     */
    List<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatusPaginated(PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus, PaginationFilter paginationFilter);

    /**
     * Update estate
     *
     * @param estate to be updated with the new Values set
     * @return
     */
    Estate update(Estate estate);

    /**
     * Gets all the details of an estate
     *
     * @param id - the id of the estate to fetch
     * @return estateDTO - object that contains all the info to an estate
     */
    EstateDTO getAllEstateDetails(long id);

    Integer countAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus);

    List<Estate> getEstatesFilteredByAllEstateCriteria(EstateSearchFilter estateSearchFilter, PaginationFilter paginationFilter);

    Integer countEstatesFilteredByAllEstateCriteria(EstateSearchFilter estateSearchFilter);
    List<String> setEstateImages(long estateId, List<String> images);
    List<String> getEstateImages(long estateId);

    List<Estate> getOwnerEstates(long ownerId, PaginationFilter paginationFilter);

    Integer countOwnersEstates(long ownerId);
}
