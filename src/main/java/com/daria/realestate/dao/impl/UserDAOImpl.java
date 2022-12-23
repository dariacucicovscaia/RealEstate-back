package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.util.DataBaseConnection;

import java.util.List;


public class UserDAOImpl extends GenericDAOAbstractImpl<User> implements UserDAO<User> {

    public UserDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, User.class);
    }

    @Override
    protected String getTableName() {
        return "realestate.user";
    }

    @Override
    public List<User> getUsersCreatedBetweenTwoTimeSlots(String startingDateTime, String endingDateTime,  PaginationFilter paginationFilter) {
        String sql = "select * from " + getTableName() + " where `createdAt` between \""
                + startingDateTime + "\" and \"" + endingDateTime +"\"";
        return getAllPaginated(sql, paginationFilter);
    }
}
