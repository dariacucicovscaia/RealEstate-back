package com.daria.realestate.service;

import java.time.LocalDateTime;

public interface ExcelReportDataService {
    //TODO we should select all appointments of a retail property for a specific period
    Object[][] prepareReportDataForEstateAppointments(LocalDateTime from, LocalDateTime to, long estateId);
}
