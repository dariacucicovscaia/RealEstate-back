package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.Address;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
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
        String sql = " SELECT * FROM realestate.estate WHERE paymentTransactionType = '"
                + paymentTransactionType.name() + "' and acquisitionStatus = '" + acquisitionStatus
                + "'  limit " + paginationFilter.getNrOfElementsWeWantDisplayed() + " offset " +
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()) + ";";


        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            return setValuesFromResultSetIntoEntityList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getEstateOwner(Estate estate) {
        String sql = " select u.* from user as u "
                + " inner join estate as e on u.id = e.owner_id "
                + " where e.id = " + estate.getId() + " ;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = new User(resultSet.getLong("id"), resultSet.getString("email"), resultSet.getString("password"));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Address getEstateAddress(Estate estate) {
        String sql = " select a.* from address as a "
                + " inner join estate as e on a.id = e.address_id "
                + " where e.id = " + estate.getId() + " ;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Address address = null;
            while (resultSet.next()) {
                address = new Address(resultSet.getLong(TABLE_COLUMN_ID), resultSet.getString("fullAddress"), resultSet.getString("city"), resultSet.getString("country"));
            }
            return address;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Estate update(Estate estate) {
        String sql = "UPDATE " + TABLE_NAME + " set acquisitionStatus = ?, paymentTransactionType = ?, createdAt =?,lastUpdatedAt=?   where id = " + estate.getId();

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(estate.getAcquisitionStatus()));
            preparedStatement.setString(2, String.valueOf(estate.getPaymentTransactionType()));
            preparedStatement.setDate(3, Date.valueOf(estate.getCreatedAt()));
            preparedStatement.setDate(4, Date.valueOf(estate.getLastUpdatedAt()));

            preparedStatement.executeUpdate();

            return getById(estate.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Estate create(Estate estate) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + TABLE_COLUMN_PAYMENT_TRANSACTION_TYPE + ", " + TABLE_COLUMN_ACQUISITION_STATUS + ", " + TABLE_COLUMN_CREATED_AT + ", " + TABLE_COLUMN_LAST_UPDATED_AT + ", " + TABLE_COLUMN_ADDRESS_ID + ", " + TABLE_COLUMN_OWNER_ID + ") " + "VALUES(?,?,?,?,?,?);";

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
                        resultSet.getDate(TABLE_COLUMN_LAST_UPDATED_AT).toLocalDate()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return estates;
    }

    @Override
    public Estate getById(long id) {
        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id + ";";
            ResultSet resultSet = statement.executeQuery(sql);

            List<Estate> estates = setValuesFromResultSetIntoEntityList(resultSet);
            return estates.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public long removeById(long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
