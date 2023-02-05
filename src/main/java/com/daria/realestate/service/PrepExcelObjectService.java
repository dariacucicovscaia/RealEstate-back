package com.daria.realestate.service;

import java.time.LocalDateTime;

public interface PrepExcelObjectService {
    /**
     * Sorts and prepares the data for the Excel insert
     * @param from
     * @param to
     * @param estateId
     * @return
     */
    Object[][] prepareReportDataForEstateAppointments(LocalDateTime from, LocalDateTime to, long estateId);
}
