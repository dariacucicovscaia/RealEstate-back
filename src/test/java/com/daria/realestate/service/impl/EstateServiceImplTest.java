package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.dao.EstatePriceDAO;
import com.daria.realestate.dao.impl.*;
import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.service.EstateService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class EstateServiceImplTest {

    @Mock
    private EstateDAO estateDAO;


    private EstateService serviceUnderTests;
    private ArgumentCaptor<Estate> estateArgumentsCaptor;

    @Before
    public void setupService() {
        estateArgumentsCaptor = ArgumentCaptor.forClass(Estate.class);
        estateDAO = mock(EstateDAOImpl.class);
        serviceUnderTests = new EstateServiceImpl((EstateDAOImpl) estateDAO);
    }

    @Test
    public void getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus() {
        PaginationFilter paginationFilter = new PaginationFilter(1, 5);

        List<Estate> estates = new ArrayList<>();
        estates.add(getSampleCreatedEstate());
        estates.add(getSampleCreatedEstate());
        estates.add(getSampleCreatedEstate());

        when(serviceUnderTests.getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType.SALE, AcquisitionStatus.SOLD, paginationFilter))
                .thenReturn(estates);


        List<Estate> fetchedEstates = serviceUnderTests
                .getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType.SALE, AcquisitionStatus.SOLD, paginationFilter);

        Assert.assertTrue(fetchedEstates.size() <= paginationFilter.getNrOfElementsWeWantDisplayed());

        for (Estate estate : fetchedEstates) {
            estates.forEach((e) -> Assert.assertTrue(e.getPaymentTransactionType().equals(estate.getPaymentTransactionType())));
            estates.forEach((e) -> Assert.assertTrue(e.getAcquisitionStatus().equals(estate.getAcquisitionStatus())));
        }

    }

    @Test
    public void getAllDetailsOfAnEstate() {
//todo implement method

    }

    @Test
    public void removeEstateById() {
        //todo implement method
    }

    @Test
    public void updateEstate() {
       Estate estate = getSampleCreatedEstate();
       estate.setId(1L);

       serviceUnderTests.updateEstate(estate);

       verify(estateDAO).update(estateArgumentsCaptor.capture());

       Estate updatedEstate = estateArgumentsCaptor.getValue();

       Assert.assertEquals(estate, updatedEstate);
    }

    private Estate getSampleCreatedEstate(){
        Address address = new Address(1L, "fullAddress", "city", "country");
        User user = new User(1L, "email", "password");
        return new Estate(PaymentTransactionType.SALE, AcquisitionStatus.SOLD, LocalDateTime.now(), LocalDateTime.now(), address, user);
    }
}