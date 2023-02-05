package com.daria.realestate.dao;

import com.daria.realestate.domain.Report;

public interface ReportDAO extends DAO<Report>{
    Report update(Report report);
}
