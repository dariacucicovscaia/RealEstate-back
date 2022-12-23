package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.OrderBy;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.util.DataBaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class EstateDAOImplTest {
    private EstateDAO<Estate> estateDAO;

    @Before
    public void init() {
        this.estateDAO = new EstateDAOImpl(DataBaseConnection.getInstance());
    }

    @Test
    public void shouldGetAllEstatesFilteredByPaymentTransactionType() {
        int nrOfElementsWeWantDisplayed = 5;
        PaymentTransactionType paymentTransactionType = PaymentTransactionType.LEASE;
        List<Estate> estateList = estateDAO.getAllEstatesFilteredByPaymentTransactionType(PaymentTransactionType.LEASE,
                new PaginationFilter(1, nrOfElementsWeWantDisplayed, "id", OrderBy.ASC)
        );

        Assert.assertTrue(estateList.size() <= nrOfElementsWeWantDisplayed);
        Assert.assertTrue(estateList.get(0).getId() < estateList.get(2).getId() );

        for (Estate estate : estateList) {
            Assert.assertTrue(estate.getPaymentTransactionType().equals(paymentTransactionType));
        }

    }

    @Test(expected = NullPointerException.class)
    public void testGetUserByIdExpectNullPointerException() {
        long id = 0;
        estateDAO.getById(id);
    }

    @Test
    public void testGetEstateByIdExpectEstateFound() {
        Estate estate = estateDAO.getById(1);
        Assert.assertNotNull(estate);
        Assert.assertEquals(EstateStatus.ON_HOLD, estate.getAcquisitionStatus());
        Assert.assertEquals(PaymentTransactionType.RENT, estate.getPaymentTransactionType());
    }

    @Test
    public void testGetAllEstates() {
        Assert.assertNotNull(estateDAO.getAll());
    }

    @Test
    public void testInsertEstate() {
        Estate estateToInsert = new Estate(1L, "RENT", "ON_HOLD");

        long createdEstateId = estateDAO.create(estateToInsert);

        Estate createdEstate = estateDAO.getById(createdEstateId);
        Assert.assertEquals(estateToInsert.getPaymentTransactionType(), createdEstate.getPaymentTransactionType());
        Assert.assertEquals(estateToInsert.getAcquisitionStatus(), createdEstate.getAcquisitionStatus());

        estateDAO.removeById(createdEstateId);
    }

    @Test
    public void testUpdateEstate() {
        Estate initialEstate = estateDAO.getById(1);
        EstateStatus oldStatus = initialEstate.getAcquisitionStatus();
        EstateStatus newStatus = EstateStatus.OPEN;

        initialEstate.setAcquisitionStatus(newStatus);
        Estate updatedEstate = estateDAO.update(initialEstate, 1);

        Assert.assertEquals(newStatus, updatedEstate.getAcquisitionStatus());


        initialEstate.setAcquisitionStatus(oldStatus);
        estateDAO.update(initialEstate, 1);
    }


    @Test
    public void testPageableEstateFetchByASCOrder() {
        String sqlStart = "select * from realestate.estate ";
        List<Estate> estates = estateDAO.getAllPaginated(sqlStart, new PaginationFilter(1, 5, "id", OrderBy.ASC));
        Assert.assertTrue(estates.get(0).getId() < estates.get(1).getId());
        Assert.assertTrue(estates.size() <= 5);
    }

    @Test
    public void testPageableEstateFetchByDESCOrder() {
        String sqlStart = "select * from realestate.estate ";
        List<Estate> estates = estateDAO.getAllPaginated(sqlStart, new PaginationFilter(1, 5, "id", OrderBy.DESC));
        Assert.assertTrue(estates.get(0).getId() > estates.get(1).getId());
        Assert.assertTrue(estates.size() <= 5);
    }

}
