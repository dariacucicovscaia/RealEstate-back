package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.dao.impl.*;
import com.daria.realestate.domain.*;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.service.EstateRegistrationService;
import org.springframework.stereotype.Service;

@Service

public class EstateRegistrationServiceImpl implements EstateRegistrationService {
    private final AddressDAO addressDAO;
    private final EstateDAO estateDAO;
    private final EstatePriceDAO priceDAO;
    private final EstateDetailsDAO estateDetailsDAO;
    private  final UserDAO userDAO;

    public EstateRegistrationServiceImpl(AddressDAOImpl addressDAO, EstateDAOImpl estateDAO, EstatePriceDAOImpl priceDAO, EstateDetailsDAOImpl estateDetailsDAO, UserDAOImpl userDAO) {
        this.addressDAO = addressDAO;
        this.estateDAO = estateDAO;
        this.priceDAO = priceDAO;
        this.estateDetailsDAO = estateDetailsDAO;
        this.userDAO = userDAO;
    }

    @Override
    public EstateDTO createEstate(EstateDTO estateDTO, long ownerId) {
        User owner = userDAO.getById(ownerId);

        Address address = addressDAO.create(new Address(estateDTO.getFullAddress(), estateDTO.getCity(), estateDTO.getCountry()));
        Estate estate = estateDAO.create(new Estate(estateDTO.getPaymentTransactionType(), estateDTO.getAcquisitionStatus(), estateDTO.getCreatedAt(), estateDTO.getLastUpdatedAt(), address, owner));
        EstatePrice price = priceDAO.create(new EstatePrice(estateDTO.getPrice(), estateDTO.getLastPriceUpdatedAt(), estateDTO.getCurrency(), estate));
        EstateDetails estateDetails = estateDetailsDAO.create(new EstateDetails(estateDTO.getSquareMeters(), estateDTO.getNumberOfRooms(), estateDTO.getNumberOfBathRooms(), estateDTO.getNumberOfGarages(), estateDTO.getYearOfConstruction(), estateDTO.getTypeOfEstate(), estate));

        return new EstateDTO(
                estate.getPaymentTransactionType(),
                estate.getAcquisitionStatus(),
                estate.getCreatedAt(),
                estate.getLastUpdatedAt(),
                estateDetails.getSquareMeters(),
                estateDetails.getNumberOfRooms(),
                estateDetails.getNumberOfBathRooms(),
                estateDTO.getNumberOfGarages(),
                estateDetails.getYearOfConstruction(),
                estateDetails.getTypeOfEstate(),
                address.getFullAddress(),
                address.getCity(),
                address.getCountry(),
                owner.getEmail(),
                price.getPrice(),
                price.getLastUpdatedAt() ,
                price.getCurrency()
        );
    }
}

