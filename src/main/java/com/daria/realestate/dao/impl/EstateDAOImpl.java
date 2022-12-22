package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstateDAOImpl extends GenericDAOAbstractImpl<Estate> implements EstateDAO<Estate> {
    public EstateDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, Estate.class);
    }

    @Override
    public List<Estate> getAllEstatesFilteredByPaymentTransactionType(PaymentTransactionType paymentTransactionType) {
        String sql = " SELECT * FROM realestate.estate WHERE paymentTransactionType = '" + paymentTransactionType.name() + "' ;";
        List<Estate> estates = new ArrayList<>();
        try (PreparedStatement preparedstatement = DataBaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = preparedstatement.executeQuery();
            while (resultSet.next()) {
                estates.add(new Estate(
                        resultSet.getLong("id"),
                        resultSet.getString("paymentTransactionType"),
                        resultSet.getString("acquisitionStatus")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return estates;
    }



    @Override
    protected String getTableName() {
        return "realestate.estate";
    }
}
