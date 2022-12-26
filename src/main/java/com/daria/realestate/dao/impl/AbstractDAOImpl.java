package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.AbstractDAO;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.util.DataBaseConnection;


import java.sql.*;
import java.util.List;

public abstract class AbstractDAOImpl<T> implements AbstractDAO<T> {

    private DataBaseConnection dataBaseConnection;

    protected AbstractDAOImpl(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @Override
    public Connection getConnection() {
        return dataBaseConnection.getConnection();
    }

    @Override
    public List<T> getAllPaginated(String selectSql, PaginationFilter paginationFilter) {

        String sql = selectSql
                + " order by \"" + paginationFilter.getColumnWeWantOrdered() + "\" " + paginationFilter.getOrderBy().name()
                + " limit " + paginationFilter.getNrOfElementsWeWantDisplayed()
                + " offset " + getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()) + ";";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
             ResultSet result = preparedStatement.executeQuery()) {

            return setValuesFromResultSetIntoEntityList(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getOffset(int pageNumber, int nrOfElementsWeWantDisplayed) {
        return (pageNumber - 1) * nrOfElementsWeWantDisplayed;
    }

    protected abstract List<T> setValuesFromResultSetIntoEntityList(ResultSet resultSet);
}
