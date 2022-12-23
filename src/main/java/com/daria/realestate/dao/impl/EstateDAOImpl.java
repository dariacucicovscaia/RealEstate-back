package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.util.DataBaseConnection;

import java.util.List;

public class EstateDAOImpl extends GenericDAOAbstractImpl<Estate> implements EstateDAO<Estate> {
    public EstateDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, Estate.class);
    }

    @Override
    public List<Estate> getAllEstatesFilteredByPaymentTransactionType(PaymentTransactionType paymentTransactionType, PaginationFilter paginationfilter) {
        String sql = " SELECT * FROM realestate.estate WHERE paymentTransactionType = '" + paymentTransactionType.name() + "'" ;

        return getAllPaginated(sql, paginationfilter);
    }



    @Override
    protected String getTableName() {
        return "realestate.estate";
    }
}
