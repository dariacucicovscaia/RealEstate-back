package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.dto.EstateDTO;
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
    public List<Estate> getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(PaymentTransactionType paymentTransactionType, AcquisitionStatus acquisitionStatus, PaginationFilter paginationFilter) {
        String sql = " SELECT * FROM realestate.estate WHERE paymentTransactionType = '"
                + paymentTransactionType.name() + "' and acquisitionStatus = '" + acquisitionStatus
                + "'  limit " + paginationFilter.getNrOfElementsWeWantDisplayed() + " offset " +
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()) + ";";


        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            return setValuesFromResultSetIntoEntityList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EstateDTO getAllEstateDetails(long id) {

        String sql = " select  " +
                " e.paymentTransactionType, e.acquisitionStatus, e.createdAt, e.lastUpdatedAt, " +
                " ed.squareMeters, ed.numberOfRooms, ed.numberOfBathrooms, ed.numberOfGarages, ed.yearOfConstruction, ed.typeOfEstate, " +
                " a.fullAddress, a.city, a.country, " +
                " u.email," +
                " price.price, price.lastUpdatedAt as lastPriceUpdate, price,concurrency " +
                " from estate as e  " +
                " inner join user as u on u.id = e.owner_id " +
                " inner join estate_details as ed on e.id = ed.estate_id " +
                " inner join address as a on e.address_id = a.id " +
                " inner join price on price.estate_id = e.id " +
                " where e.id = " + id + " ;";

        EstateDTO estateDTO = null;
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                estateDTO = new EstateDTO(
                        PaymentTransactionType.valueOf(resultSet.getString("paymentTransactionType")),
                        AcquisitionStatus.valueOf(resultSet.getString("acquisitionStatus")),
                        resultSet.getTimestamp("createdAt").toLocalDateTime(),
                        resultSet.getTimestamp("lastUpdatedAt").toLocalDateTime(),

                        resultSet.getInt("squareMeters"),
                        resultSet.getInt("numberOfRooms"),
                        resultSet.getInt("numberOfBathRooms"),
                        resultSet.getInt("numberOfGarages"),
                        resultSet.getDate("yearOfConstruction").toLocalDate(),
                        TypeOfEstate.valueOf(resultSet.getString("typeOfEstate")),

                        resultSet.getString("fullAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("country"),

                        resultSet.getString("email"),

                        resultSet.getLong("price"),
                        resultSet.getTimestamp("lastPriceUpdate").toLocalDateTime()
                        ,resultSet.getString("concurrency")
                );
            }
            return estateDTO;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Estate update(Estate estate) {
        String sql = "UPDATE " + TABLE_NAME + " set acquisitionStatus = ?, paymentTransactionType = ?, createdAt =?,lastUpdatedAt=?   where id = " + estate.getId();

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(estate.getAcquisitionStatus()));
            preparedStatement.setString(2, String.valueOf(estate.getPaymentTransactionType()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(estate.getCreatedAt()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(estate.getLastUpdatedAt()));

            preparedStatement.executeUpdate();

            return getById(estate.getId());
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public Estate create(Estate estate) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + TABLE_COLUMN_PAYMENT_TRANSACTION_TYPE + ", " + TABLE_COLUMN_ACQUISITION_STATUS + ", " + TABLE_COLUMN_CREATED_AT + ", " + TABLE_COLUMN_LAST_UPDATED_AT + ", " + TABLE_COLUMN_ADDRESS_ID + ", " + TABLE_COLUMN_OWNER_ID + ") " + "VALUES(?,?,?,?,?,?);";

        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, estate.getPaymentTransactionType().name());
            preparedStatement.setString(2, estate.getAcquisitionStatus().name());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(estate.getCreatedAt()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(estate.getLastUpdatedAt()));
            preparedStatement.setLong(5, estate.getAddress().getId());
            preparedStatement.setLong(6, estate.getOwner().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                estate.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e);
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
                        resultSet.getTimestamp(TABLE_COLUMN_CREATED_AT).toLocalDateTime(),
                        resultSet.getTimestamp(TABLE_COLUMN_LAST_UPDATED_AT).toLocalDateTime()));
            }
        } catch (SQLException e) {
            logger.error(e);
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
            logger.error(e);
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
            logger.error(e);
            throw new RuntimeException(e);
        }
        return id;
    }
}
