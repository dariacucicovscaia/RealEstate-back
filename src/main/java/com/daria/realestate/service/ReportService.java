package com.daria.realestate.service;

import com.daria.realestate.domain.Report;
import com.daria.realestate.dto.Page;

import java.io.File;
import java.time.LocalDateTime;

public interface ReportService {

    String generateReport(LocalDateTime from, LocalDateTime to, long estateId);

    File getReport(long estateId);
    Page<Report> getAllReportsOfAUser(long userId, int pageSize, int pageNumber);

}
