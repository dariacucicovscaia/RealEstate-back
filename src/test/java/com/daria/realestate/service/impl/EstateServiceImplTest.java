package com.daria.realestate.service.impl;

import com.daria.realestate.dao.*;
import com.daria.realestate.dao.impl.*;
import com.daria.realestate.domain.*;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.service.EstateService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class EstateServiceImplTest {

    @Mock
    private EstateDAO estateDAO;
    @Mock
    private AddressDAO addressDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private UserRoleDAO userRoleDAO;
    @Mock
    private EstatePriceDAO priceDAO;
    @Mock
    private EstateDetailsDAO estateDetailsDAO;

    private EstateService serviceUnderTests;

    @Before
    public void setupService() {
        estateDAO = mock(EstateDAOImpl.class);
        addressDAO = mock(AddressDAOImpl.class);
        userDAO = mock(UserDAOImpl.class);
        userRoleDAO = mock(UserRoleDAOImpl.class);
        priceDAO = mock(EstatePriceDAOImpl.class);
        estateDetailsDAO = mock(EstateDetailsDAOImpl.class);

        serviceUnderTests = new EstateServiceImpl( estateDAO,  addressDAO, priceDAO, estateDetailsDAO, userDAO, userRoleDAO);
    }

    @Test
    public void createEstate() {
        Estate estate = getSampleCreatedEstate();
        when(estateDAO.create(estate)).thenReturn(estate);

        Estate createdEstate = serviceUnderTests.createEstate(estate);
        verify(estateDAO).create(estate);
        Assert.assertNotNull(createdEstate);
    }

    @Test
    public void getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus() {
        PaginationFilter paginationFilter = new PaginationFilter(1, 5);

        List<Estate> estates = new ArrayList<>();
        estates.add(getSampleCreatedEstate());
        estates.add(getSampleCreatedEstate());
        estates.add(getSampleCreatedEstate());

        when(estateDAO.getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatusPaginated(PaymentTransactionType.SALE, AcquisitionStatus.SOLD, paginationFilter)).thenReturn(estates);


//        List<Estate> fetchedEstates = serviceUnderTests.getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType.SALE, AcquisitionStatus.SOLD, paginationFilter);
//
//        Assert.assertNotNull(fetchedEstates);
//        verify(estateDAO).getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatusPaginated(PaymentTransactionType.SALE, AcquisitionStatus.SOLD, paginationFilter);

    }

    @Test
    public void getAllDetailsOfAnEstate() {
        EstateDTO estateDTO = new EstateDTO(PaymentTransactionType.SALE, AcquisitionStatus.SOLD, LocalDateTime.of(2022, 12, 12, 20, 10, 20), LocalDateTime.of(2022, 12, 12, 20, 10, 20), 25, 2, 2, 1, LocalDate.of(2023, 01, 01), TypeOfEstate.TOWNHOUSE, "fullAddress", "city", "country", "email", 10000L, LocalDateTime.of(2022, 12, 12, 20, 10, 20), "EUR");

        when(estateDAO.getAllEstateDetails(1L)).thenReturn(estateDTO);

        EstateDTO fetchedTestData = serviceUnderTests.getAllDetailsOfAnEstate(1L);

        Assert.assertNotNull(fetchedTestData);
        verify(estateDAO).getAllEstateDetails(1L);
    }


    @Test
    public void updateEstate() {
        Estate estate = getSampleCreatedEstate();
        when(estateDAO.update(estate)).thenReturn(estate);

        Estate createdTestData = serviceUnderTests.updateEstate(estate);

        verify(estateDAO).update(estate);
        Assert.assertNotNull(createdTestData);
    }

    private Estate getSampleCreatedEstate() {
        Address address = new Address(1L, "fullAddress", "city", "country");
        User user = new User(1L, "email", "password");
        return new Estate(PaymentTransactionType.SALE, AcquisitionStatus.SOLD, LocalDateTime.now(), LocalDateTime.now(), address, user);
    }
}