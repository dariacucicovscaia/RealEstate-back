package com.daria.realestate.service;

import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.EstateDTO;

import java.util.List;

public interface EstateService {
    List<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus, PaginationFilter paginationFilter);
    EstateDTO getAllDetailsOfAnEstate(long estateId);

    Estate getEstateById(Long id);
    Long removeEstateById(Long id);
    Estate updateEstate(Estate estate);
}
