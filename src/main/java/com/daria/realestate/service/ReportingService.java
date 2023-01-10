package com.daria.realestate.service;

import com.daria.realestate.domain.User;
import com.daria.realestate.dto.AppointmentDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportingService {
    //TODO we should select all appointments of a retail property for a specific period
    //TODO distribute file creation and database fetching
    Boolean createAppointmentReportWithTimeInterval(LocalDateTime from, LocalDateTime to, List<User> users);
    List<AppointmentDTO> readDataOfAUserFromExcelFile(String user) throws IOException;
}
