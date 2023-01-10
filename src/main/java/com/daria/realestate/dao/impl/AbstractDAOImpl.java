package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.DAO;
import com.daria.realestate.util.DataBaseConnection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.List;

public abstract class AbstractDAOImpl<T> implements DAO<T> {

    protected static final Logger logger = LogManager.getLogger(AbstractDAOImpl.class);

    private final DataBaseConnection dataBaseConnection;

    protected AbstractDAOImpl(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    public Connection getConnection() {
        return dataBaseConnection.getConnection();
    }

    protected int getOffset(int pageNumber, int nrOfElementsWeWantDisplayed) {
        return (pageNumber - 1) * nrOfElementsWeWantDisplayed;
    }

    protected abstract List<T> setValuesFromResultSetIntoEntityList(ResultSet resultSet);
}
