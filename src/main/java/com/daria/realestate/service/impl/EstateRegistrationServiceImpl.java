package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.dao.EstatePriceDAO;
import com.daria.realestate.dao.impl.AddressDAOImpl;
import com.daria.realestate.dao.impl.EstateDAOImpl;
import com.daria.realestate.dao.impl.EstateDetailsDAOImpl;
import com.daria.realestate.dao.impl.EstatePriceDAOImpl;
import com.daria.realestate.domain.*;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.service.EstateRegistrationService;

public class EstateRegistrationServiceImpl implements EstateRegistrationService {
    private final AddressDAO addressDAO;
    private final EstateDAO estateDAO;
    private final EstatePriceDAO priceDAO;
    private final EstateDetailsDAO estateDetailsDAO;

    public EstateRegistrationServiceImpl(AddressDAOImpl addressDAO, EstateDAOImpl estateDAO, EstatePriceDAOImpl priceDAO, EstateDetailsDAOImpl estateDetailsDAO) {
        this.addressDAO = addressDAO;
        this.estateDAO = estateDAO;
        this.priceDAO = priceDAO;
        this.estateDetailsDAO = estateDetailsDAO;
    }

    @Override
    public EstateDTO createEstate(EstateDTO estateDTO, User owner) {
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
                estateDTO.getFirstName(),
                estateDTO.getLastName(),
                estateDTO.getPhoneNumber(),
                price.getPrice(),
                price.getLastUpdatedAt() ,
                price.getCurrency()
        );
    }
}
