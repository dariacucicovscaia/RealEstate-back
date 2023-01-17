package com.daria.realestate.service.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.dao.impl.EstateDAOImpl;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.service.EstateService;

import java.util.List;

public class EstateServiceImpl implements EstateService {
    private final EstateDAO estateDAO;

    public EstateServiceImpl(EstateDAOImpl estateDAO) {
        this.estateDAO = estateDAO;
    }

    @Override
    public List<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus, PaginationFilter paginationFilter) {
        return estateDAO.getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(paymentTransactionType, acquisitionStatus, paginationFilter);
    }

    @Override
    public Estate createEstate(Estate estate){
        return estateDAO.create(estate);
    }

    @Override
    public EstateDTO getAllDetailsOfAnEstate(long estateId) {
        return estateDAO.getAllEstateDetails(estateId);
    }

    @Override
    public Estate getEstateById(Long id) {
        return estateDAO.getById(id);
    }

    @Override
    public Estate updateEstate(Estate estate) {
        return estateDAO.update(estate);
    }

}
