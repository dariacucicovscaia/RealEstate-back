package com.daria.realestate.dao;

import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.Report;
import com.daria.realestate.dto.enums.FileLocation;

import java.util.List;

public interface ReportDAO extends DAO<Report>{
    Report update(Report report);
    Report getByIdAndLocation(long id, FileLocation location);
    List<Report> getAllReportsOfAUser(long userId, PaginationFilter paginationFilter);
    Integer countAllReportsOfAUser(long userId);
}
