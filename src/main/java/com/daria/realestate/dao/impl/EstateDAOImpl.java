package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.EstateStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstateDAOImpl extends AbstractDAOImpl<Estate> implements EstateDAO {
    public EstateDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    private final String TABLE_NAME = "realestate.estate";
    private final String TABLE_COLUMN_ID = "id";
    private final String TABLE_COLUMN_PAYMENT_TRANSACTION_TYPE = "paymentTransactionType";
    private final String TABLE_COLUMN_ACQUISITION_STATUS = "acquisitionStatus";
    private final String TABLE_COLUMN_CREATED_AT = "createdAt";
    private final String TABLE_COLUMN_LAST_UPDATED_AT = "lastUpdatedAt";
    private final String TABLE_COLUMN_ADDRESS_ID = "address_id";
    private final String TABLE_COLUMN_OWNER_ID = "owner_id";


    @Override
    public List<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType paymentTransactionType, EstateStatus acquisitionStatus, PaginationFilter paginationFilter) {
        String sql = " SELECT * FROM realestate.estate WHERE paymentTransactionType = '" + paymentTransactionType.name() + "' and acquisitionStatus = '" +
                acquisitionStatus + "'";

        return getAllPaginated(sql, paginationFilter);
    }

    @Override
    //todo change string into string builder
    public Estate updateEstateAcquisitionStatus(long id, EstateStatus acquisitionStatus) {
        String sql = "UPDATE "+ TABLE_NAME + " set acquisitionStatus = ? where id = "+id+";";

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(acquisitionStatus));

            int updated = preparedStatement.executeUpdate();
            if (updated == 1) {
                return getEstateById(id);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Estate createEstate(Estate estate) {
        String sql = "INSERT INTO " + TABLE_NAME + " ("
                + TABLE_COLUMN_PAYMENT_TRANSACTION_TYPE + ", "
                + TABLE_COLUMN_ACQUISITION_STATUS + ", "
                + TABLE_COLUMN_CREATED_AT + ", "
                + TABLE_COLUMN_LAST_UPDATED_AT + ", "
                + TABLE_COLUMN_ADDRESS_ID + ", "
                + TABLE_COLUMN_OWNER_ID + ") "
                + "VALUES(?,?,?,?,?,?);";

        try (PreparedStatement preparedStatement = DataBaseConnection
                .getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, estate.getPaymentTransactionType().name());
            preparedStatement.setString(2, estate.getAcquisitionStatus().name());
            preparedStatement.setDate(3, Date.valueOf(estate.getCreatedAt()));
            preparedStatement.setDate(4, Date.valueOf(estate.getLastUpdatedAt()));
            preparedStatement.setLong(5, estate.getAddress().getId());
            preparedStatement.setLong(6, estate.getOwner().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                estate.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return estate;
    }

    @Override
    protected List<Estate> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<Estate> estates = new ArrayList<>();
        try {
            while (resultSet.next()) {
                estates.add(new Estate(
                        resultSet.getLong(TABLE_COLUMN_ID),
                        resultSet.getString(TABLE_COLUMN_PAYMENT_TRANSACTION_TYPE),
                        resultSet.getString(TABLE_COLUMN_ACQUISITION_STATUS),
                        resultSet.getDate(TABLE_COLUMN_CREATED_AT).toLocalDate(),
                        resultSet.getDate(TABLE_COLUMN_LAST_UPDATED_AT).toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return estates;
    }

    @Override
    public Estate getEstateById(long id) {
        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {

            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(TABLE_NAME);
            sql.append(" WHERE id = ");
            sql.append(id);
            sql.append(";");
            ResultSet resultSet = statement.executeQuery(sql.toString());

            List<Estate> estates = setValuesFromResultSetIntoEntityList(resultSet);
            return estates.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public long removeEstateById(long id) {
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(TABLE_NAME);
        sql.append(" WHERE id = ?;");

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql.toString())) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

}
