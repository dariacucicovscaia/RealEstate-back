package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstatePriceDAO;
import com.daria.realestate.domain.EstatePrice;
import com.daria.realestate.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstatePriceDAOImpl extends AbstractDAOImpl<EstatePrice> implements EstatePriceDAO {
    private static final String TABLE_NAME = "realestate.price";

    private static final String TABLE_COLUMN_ID = "id";
    private static final String TABLE_COLUMN_PRICE = "price";
    private static final String TABLE_COLUMN_LAST_UPDATED_AT = "lastUpdatedAt";
    private static final String TABLE_COLUMN_CONCURRENCY = "concurrency";
    private static final String TABLE_COLUMN_ESTATE_ID = "estate_id";

    protected EstatePriceDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    @Override
    public EstatePrice create(EstatePrice price) {
        String sql = "INSERT INTO " + TABLE_NAME + " ("
                + TABLE_COLUMN_PRICE + ", "
                + TABLE_COLUMN_LAST_UPDATED_AT + ", "
                + TABLE_COLUMN_CONCURRENCY + ", "
                + TABLE_COLUMN_ESTATE_ID + ") " + "VALUES(?,?,?,?);";
        try (PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setLong(1, price.getPrice());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(price.getLastUpdatedAt()));
            preparedStatement.setString(3, price.getCurrency());
            preparedStatement.setLong(4, price.getEstate().getId());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                price.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getById(price.getId());
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

    @Override
    public EstatePrice getById(long id) {
        try (Statement statement = DataBaseConnection.getConnection().createStatement()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id + ";";
            ResultSet resultSet = statement.executeQuery(sql);

            return setValuesFromResultSetIntoEntityList(resultSet).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<EstatePrice> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<EstatePrice> prices = new ArrayList<>();
        try {
            while (resultSet.next()) {
                prices.add(new EstatePrice(
                        resultSet.getLong(TABLE_COLUMN_ID),
                        resultSet.getLong(TABLE_COLUMN_PRICE),
                        resultSet.getTimestamp(TABLE_COLUMN_LAST_UPDATED_AT).toLocalDateTime(),
                        resultSet.getString(TABLE_COLUMN_CONCURRENCY)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prices;
    }
}
