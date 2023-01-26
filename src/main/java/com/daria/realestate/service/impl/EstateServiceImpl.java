package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.dao.impl.AddressDAOImpl;
import com.daria.realestate.dao.impl.EstateDAOImpl;
import com.daria.realestate.dao.impl.UserDAOImpl;
import com.daria.realestate.dao.impl.UserRoleDAOImpl;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.service.EstateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstateServiceImpl implements EstateService {
    private final EstateDAO estateDAO;
    private final AddressDAO addressDAO;
    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;

    public EstateServiceImpl(EstateDAOImpl estateDAO, AddressDAOImpl addressDAO, UserDAOImpl userDAO, UserRoleDAOImpl userRoleDAO) {
        this.estateDAO = estateDAO;
        this.addressDAO = addressDAO;
        this.userDAO = userDAO;
        this.userRoleDAO = userRoleDAO;
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
        Estate estate = estateDAO.getById(id);
        estate.setAddress(addressDAO.getAddressOfAnEstate(id));
        User owner = userDAO.getOwnerOfAnEstate(id);
        owner.setRoles(userRoleDAO.getRolesOfAUser(id));
        estate.setOwner(owner);
        return estate;
    }

    @Override
    public Estate updateEstate(Estate estate) {
        return estateDAO.update(estate);
    }

}
