package com.daria.realestate.service;

import java.time.LocalDateTime;

public interface ExcelReportDataService {
    //TODO we should select all appointments of a retail property for a specific period

    /**
     * Sorts and prepares the data for the Excel inse
     * @param from
     * @param to
     * @param estateId
     * @return
     */
    Object[][] prepareReportDataForEstateAppointments(LocalDateTime from, LocalDateTime to, long estateId);
}
