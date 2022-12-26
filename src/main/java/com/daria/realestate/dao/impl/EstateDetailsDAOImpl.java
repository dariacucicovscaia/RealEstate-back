package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.domain.EstateDetails;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.*;
import java.time.LocalDate;
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
                        resultSet.getString(TABLE_COLUMN_TYPE_OF_ESTATE),
                        resultSet.getLong(TABLE_COLUMN_ESTATE_ID)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return estateDetails;
    }

    public EstateDetails createEstateDetails(EstateDetails estateDetails) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(TABLE_NAME);
        sql.append(" (");
        sql.append(TABLE_COLUMN_SQUARE_METERS); sql.append(", ");
        sql.append(TABLE_COLUMN_NUMBER_OF_ROOMS); sql.append(", ");
        sql.append(TABLE_COLUMN_NUMBER_OF_BATHROOMS); sql.append(", ");
        sql.append(TABLE_COLUMN_NUMBER_OF_GARAGES); sql.append(", ");
        sql.append(TABLE_COLUMN_YEAR_OF_CONSTRUCTION); sql.append(", ");
        sql.append(TABLE_COLUMN_TYPE_OF_ESTATE); sql.append(", ");
        sql.append(TABLE_COLUMN_ESTATE_ID); sql.append(") ");
        sql.append("VALUES(?,?,?,?,?,?,?);");

        try (PreparedStatement preparedStatement = DataBaseConnection
                .getConnection()
                .prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setInt(1, estateDetails.getSquareMeters());
            preparedStatement.setInt(2, estateDetails.getNumberOfRooms());
            preparedStatement.setInt(3, estateDetails.getNumberOfBathRooms());
            preparedStatement.setInt(4, estateDetails.getNumberOfGarages());
            preparedStatement.setDate(5, Date.valueOf(estateDetails.getYearOfConstruction()));
            preparedStatement.setString(6, String.valueOf(estateDetails.getTypeOfEstate()));
            preparedStatement.setLong(7, estateDetails.getEstate().getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return estateDetails;
    }

    public List<EstateDetails> getFilteredEstateDetailsByAllParameters(int squareMeters, int numberOfRooms, int numberOfBathRooms, int numberOfGarages, LocalDate yearOfConstruction, String typeOfEstate, PaginationFilter paginationFilter) {
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(TABLE_NAME);
        sql.append(" where squareMeters = "); sql.append(squareMeters);
        sql.append(" and numberOfRooms = "); sql.append(numberOfRooms);
        sql.append(" and numberOfBathRooms="); sql.append(numberOfBathRooms);
        sql.append(" and numberOfGarages="); sql.append(numberOfGarages);
        sql.append(" and yearOfConstruction=\""); sql.append(Date.valueOf(yearOfConstruction));
        sql.append("\" and typeOfEstate like '%"); sql.append(typeOfEstate);
        sql.append("%'");

        return getAllPaginated(sql.toString(), paginationFilter);

    }
    @Override
    public long removeEstateDetailsById(long id) {
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(TABLE_NAME);
        sql.append(" WHERE estate_id = ?;");

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql.toString())) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}