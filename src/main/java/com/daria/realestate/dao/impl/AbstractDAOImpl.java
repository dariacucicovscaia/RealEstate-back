package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.DAO;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


public abstract class AbstractDAOImpl<T> implements DAO<T> {

    private final JdbcTemplate jdbcTemplate;

    protected AbstractDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    protected int getOffset(int pageNumber, int nrOfElementsWeWantDisplayed) {
        return (pageNumber - 1) * nrOfElementsWeWantDisplayed;
    }


}
