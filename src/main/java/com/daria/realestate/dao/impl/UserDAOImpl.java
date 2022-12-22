package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.domain.User;
import com.daria.realestate.util.DataBaseConnection;


public class UserDAOImpl extends GenericDAOAbstractImpl<User> implements UserDAO<User> {

    public UserDAOImpl(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, User.class);
    }

    @Override
    protected String getTableName() {
        return "realestate.user";
    }
}
