package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.domain.EstateDetails;
import com.daria.realestate.dbconnection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstateDetailsDAOImpl extends AbstractDAOImpl<EstateDetails> implements EstateDetailsDAO {
    private final String TABLE_NAME = "realestate.estate_details";
    private final String TABLE_COLUMN_SQUARE_METERS = "squareMeters";
    private final String TABLE_COLUMN_NUMBER_OF_ROOMS = "numberOfRooms";
    private final String TABLE_COLUMN_NUMBER_OF_BATHROOMS = "numberOfBathRooms";
    private final String TABLE_COLUMN_NUMBER_OF_GARAGES = "numberOfGarages";
    private final String TABLE_COLUMN_YEAR_OF_CONSTRUCTION = "yearOfConstruction";
    private final String TABLE_COLUMN_TYPE_OF_ESTATE = "typeOfEstate";
    private final String TABLE_COLUMN_ESTATE_ID = "estate_id";

    protected EstateDetailsDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    @Override
    protected List<EstateDetails> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<EstateDetails> estateDetails = new ArrayList<>();
        try {
            while (resultSet.next()) {
                estateDetails.add(new EstateDetails(
                        resultSet.getInt(TABLE_COLUMN_SQUARE_METERS),
                        resultSet.getInt(TABLE_COLUMN_NUMBER_OF_ROOMS),
                        resultSet.getInt(TABLE_COLUMN_NUMBER_OF_BATHROOMS),
                        resultSet.getInt(TABLE_COLUMN_NUMBER_OF_GARAGES),
                        resultSet.getDate(TABLE_COLUMN_YEAR_OF_CONSTRUCTION).toLocalDate(),
                        resultSet.getString(TABLE_COLUMN_TYPE_OF_ESTATE)
                ));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return estateDetails;
    }

    @Override
    public EstateDetails create(EstateDetails estateDetails) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + TABLE_COLUMN_SQUARE_METERS + ", " + TABLE_COLUMN_NUMBER_OF_ROOMS + ", " + TABLE_COLUMN_NUMBER_OF_BATHROOMS + ", " + TABLE_COLUMN_NUMBER_OF_GARAGES + ", " + TABLE_COLUMN_YEAR_OF_CONSTRUCTION + ", " + TABLE_COLUMN_TYPE_OF_ESTATE + ", " + TABLE_COLUMN_ESTATE_ID + ") " + "VALUES(?,?,?,?,?,?,?);";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, estateDetails.getSquareMeters());
            preparedStatement.setInt(2, estateDetails.getNumberOfRooms());
            preparedStatement.setInt(3, estateDetails.getNumberOfBathRooms());
            preparedStatement.setInt(4, estateDetails.getNumberOfGarages());
            preparedStatement.setDate(5, Date.valueOf(estateDetails.getYearOfConstruction()));
            preparedStatement.setString(6, String.valueOf(estateDetails.getTypeOfEstate()));
            preparedStatement.setLong(7, estateDetails.getEstate().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return estateDetails;
    }


    @Override
    public long removeById(long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE estate_id = ?;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return id;
    }


    @Override
    public EstateDetails getById(long id) {
        try (Statement statement = getConnection().createStatement()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE estate_id = " + id + ";";
            ResultSet resultSet = statement.executeQuery(sql);

            List<EstateDetails> estateDetails = setValuesFromResultSetIntoEntityList(resultSet);
            return estateDetails.get(0);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}