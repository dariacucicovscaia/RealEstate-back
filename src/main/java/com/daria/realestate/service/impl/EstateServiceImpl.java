package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.dto.EstateSearchFilter;
import com.daria.realestate.dto.Page;
import com.daria.realestate.service.EstateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstateServiceImpl implements EstateService {
    private final EstateDAO estateDAO;
    private final AddressDAO addressDAO;
    private final EstatePriceDAO priceDAO;
    private final EstateDetailsDAO estateDetailsDAO;
    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;

    public EstateServiceImpl(EstateDAO estateDAO, AddressDAO addressDAO, EstatePriceDAO priceDAO, EstateDetailsDAO estateDetailsDAO, UserDAO userDAO, UserRoleDAO userRoleDAO) {
        this.estateDAO = estateDAO;
        this.addressDAO = addressDAO;
        this.priceDAO = priceDAO;
        this.estateDetailsDAO = estateDetailsDAO;
        this.userDAO = userDAO;
        this.userRoleDAO = userRoleDAO;
    }

    @Override
    public Page<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(int elementsPerPage, int pageNumber, PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus) {
        List<Estate> filteredEstateList = estateDAO.getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatusPaginated(paymentTransactionType, acquisitionStatus, new PaginationFilter(pageNumber, elementsPerPage));
        int totalPageCount = estateDAO.countAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(paymentTransactionType, acquisitionStatus);

        return new Page<>(filteredEstateList, totalPageCount, pageNumber, elementsPerPage);
    }

    @Override
    public Estate createEstate(Estate estate) {
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

    @Override
    public Page<Estate> getEstatesFilteredByAllEstateCriteria(EstateSearchFilter estateSearchFilter, int elementsPerPage, int pageNumber) {
        List<Estate> content = estateDAO.getEstatesFilteredByAllEstateCriteria( estateSearchFilter,  new PaginationFilter(pageNumber, elementsPerPage));
        int totalPageCount = estateDAO.countEstatesFilteredByAllEstateCriteria(estateSearchFilter);

        return new Page<>( content, totalPageCount, pageNumber, elementsPerPage);
    }
    @Override
    public EstateDTO createEstate(EstateDTO estateDTO) {
        User owner = userDAO.getUserByEmail(estateDTO.getEmail());

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
