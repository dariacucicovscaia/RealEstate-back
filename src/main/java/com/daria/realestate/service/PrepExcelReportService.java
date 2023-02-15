package com.daria.realestate.service;

import org.apache.poi.ss.usermodel.Workbook;

import java.time.LocalDateTime;

public interface PrepExcelReportService {
    Object[][] prepareReportDataForEstateAppointments(LocalDateTime from, LocalDateTime to, long estateId);
    Workbook generateReport(LocalDateTime from, LocalDateTime to, long estateId);
    String generateLocalFileName(long estateId);
}
