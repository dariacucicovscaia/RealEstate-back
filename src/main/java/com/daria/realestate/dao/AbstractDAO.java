package com.daria.realestate.dao;

import com.daria.realestate.domain.PaginationFilter;

import java.sql.Connection;
import java.util.List;

public interface AbstractDAO<T> {

    Connection getConnection();
    List<T> getAllPaginated(String selectSql, PaginationFilter paginationFilter);
}
