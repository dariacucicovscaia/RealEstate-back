package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.EstatePriceDAO;
import com.daria.realestate.dao.mappers.EstatePriceMapper;
import com.daria.realestate.domain.EstatePrice;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
@Repository
public class EstatePriceDAOImpl extends AbstractDAOImpl<EstatePrice> implements EstatePriceDAO {
    private static final String SQL_CREATE_ESTATE_PRICE = " insert into price ( price, last_updated_at, concurrency, estate_id )  values ( ? , ? , ? , ? ) ";
    private static final String SQL_DELETE_ESTATE_PRICE = " delete from price where id = ? ";
    private static final String SQL_GET_PRICE_BY_ID = "SELECT * FROM price WHERE id = ? ";

    protected EstatePriceDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public EstatePrice create(EstatePrice price) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate()
                .update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_ESTATE_PRICE, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setLong(1, price.getPrice());
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(price.getLastUpdatedAt()));
                    preparedStatement.setString(3, price.getCurrency());
                    preparedStatement.setLong(4, price.getEstate().getId());
                    return preparedStatement;
                }, keyHolder);
        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public int removeById(long id) {
        return getJdbcTemplate().update(SQL_DELETE_ESTATE_PRICE, id);
    }

    @Override
    public EstatePrice getById(long id) {
        return getJdbcTemplate().queryForObject(SQL_GET_PRICE_BY_ID, new EstatePriceMapper(), id);
    }

}
