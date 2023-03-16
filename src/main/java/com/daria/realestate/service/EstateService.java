package com.daria.realestate.service;

import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.dto.EstateSearchFilter;
import com.daria.realestate.dto.Page;

public interface EstateService {
    Page<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(int pageSize, int pageNumber,PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus);
    EstateDTO getAllDetailsOfAnEstate(long estateId);
    Estate createEstate(Estate estate);
    Estate getEstateById(Long id);
    Estate updateEstate(Estate estate);
    Page<Estate> getEstatesFilteredByAllEstateCriteria(EstateSearchFilter estateSearchFilter, int pageSize, int pageNumber);
    EstateDTO createEstate(EstateDTO estateDTO);

    Page<Estate> getOwnerEstates(long ownerId, int page, int pageSize);
}
