package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AddressDAO;
import com.daria.realestate.domain.Address;
import com.daria.realestate.dbconnection.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressDAOImpl extends AbstractDAOImpl<Address> implements AddressDAO {
    public AddressDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection);
    }

    private final static String TABLE_NAME = "address";

    @Override
    public Address create(Address address) {
        String sql = " INSERT INTO " + TABLE_NAME + " (fullAddress, city, country) " + " VALUES( ? , ? , ? ) ";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, address.getFullAddress());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getCountry());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                address.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return getById(address.getId());
    }

    @Override
    public long removeById(long id) {
        String sql = " DELETE FROM " + TABLE_NAME + " WHERE id = ? ";

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
    public Address getById(long id) {
        try (Statement statement = getConnection().createStatement()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);

            List<Address> addresses = setValuesFromResultSetIntoEntityList(resultSet);
            return addresses.get(0);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Address> setValuesFromResultSetIntoEntityList(ResultSet resultSet) {
        List<Address> addresses = new ArrayList<>();
        try {
            while (resultSet.next()) {
                addresses.add(new Address(resultSet.getLong("id"), resultSet.getString("fullAddress"), resultSet.getString("city"), resultSet.getString("country")));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return addresses;
    }

    @Override
    public Address update(Address address) {
        String sql = "UPDATE " + TABLE_NAME + " SET  fullAddress = ?, city  = ?, country = ? WHERE id = " + address.getId() + ";";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, address.getFullAddress());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getCountry());

            int updated = preparedStatement.executeUpdate();
            if (updated == 1) {
                return address;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Address getAddressOfAnEstate(long estateId) {
        String sql = " select a.* from address as a " +
                " inner join estate as e on e.address_id = a.id " +
                " where e.id = " + estateId;

        try(Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
            return setValuesFromResultSetIntoEntityList(resultSet).get(0);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
