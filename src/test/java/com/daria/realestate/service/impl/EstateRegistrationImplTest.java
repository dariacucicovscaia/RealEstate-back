package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.dao.impl.AddressDAOImpl;
import com.daria.realestate.dao.impl.EstateDAOImpl;
import com.daria.realestate.dao.impl.EstateDetailsDAOImpl;
import com.daria.realestate.dao.impl.EstatePriceDAOImpl;
import com.daria.realestate.service.EstateRegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import static org.mockito.Mockito.*;

public class EstateRegistrationImplTest {
    @Mock
    private AddressDAO addressDAO;
    @Mock
    private EstateDAO estateDAO;
    @Mock
    private EstatePriceDAO priceDAO;
    @Mock
    private EstateDetailsDAO estateDetailsDAO;

    private EstateRegistrationService serviceUnderTest;

    @Before
    public void before() {
        addressDAO = mock(AddressDAOImpl.class);
        estateDAO = mock(EstateDAOImpl.class);
        priceDAO = mock(EstatePriceDAOImpl.class);
        estateDetailsDAO = mock(EstateDetailsDAOImpl.class);

        serviceUnderTest = new EstateRegistrationServiceImpl((AddressDAOImpl) addressDAO, (EstateDAOImpl) estateDAO, (EstatePriceDAOImpl) priceDAO, (EstateDetailsDAOImpl) estateDetailsDAO);
    }

    @Test
    public void createEstate() {
//        User owner = new User(1L, "email", "password");
//        Address address = new Address(1L, "fullAddress", "city", "country");
//        Estate estate = new Estate(PaymentTransactionType.SALE, AcquisitionStatus.SOLD, LocalDateTime.now(), LocalDateTime.now(), address, owner);
//        EstatePrice price = new EstatePrice(10000L, LocalDateTime.of(2022, 12, 12, 20, 10, 20), "EUR", estate);
//        EstateDetails estateDetails = new EstateDetails(25, 3, 2, 1, LocalDate.of(2023, 01, 01), TypeOfEstate.TOWNHOUSE, estate);
//
//        EstateDTO estateDTO = new EstateDTO(estate.getPaymentTransactionType(), estate.getAcquisitionStatus(), estate.getCreatedAt(), estate.getLastUpdatedAt(), estateDetails.getSquareMeters(), estateDetails.getNumberOfRooms(), estateDetails.getNumberOfBathRooms(), estateDetails.getNumberOfGarages(), estateDetails.getYearOfConstruction(), estateDetails.getTypeOfEstate(), address.getFullAddress(), address.getCity(), address.getCountry(), owner.getEmail(), price.getPrice(), price.getLastUpdatedAt(), price.getCurrency());
//
//        when(addressDAO.create(address)).thenReturn(address);
//        when(estateDAO.create(estate)).thenReturn(estate);
//        when(priceDAO.create(price)).thenReturn(price);
//        when(estateDetailsDAO.create(estateDetails)).thenReturn(estateDetails);
//
//        when(serviceUnderTest.createEstate(estateDTO, owner)).thenReturn(estateDTO);
//
//        EstateDTO createdTestData = serviceUnderTest.createEstate(estateDTO, owner);
//
//        Assert.assertNotNull(createdTestData);
//
//        verify(addressDAO).create(address);
//        verify(estateDAO).create(estate);
//        verify(estateDetailsDAO).create(estateDetails);
//        verify(priceDAO).create(price);
    }

}