package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.impl.AppointmentDAOImpl;
import com.daria.realestate.dto.AppointmentReportDTO;
import com.daria.realestate.service.PrepExcelObjectService;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrepExcelObjectServiceImpl implements PrepExcelObjectService {

    private final AppointmentDAO appointmentDAO;

    public PrepExcelObjectServiceImpl(AppointmentDAOImpl appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Override
    public Object[][] prepareReportDataForEstateAppointments(LocalDateTime from, LocalDateTime to, long estateId) {
        String[] headers = {"Start", "End", "Email", "FirstName", "LastName", "PhoneNumber", "AppointmentStatus"};
        List<AppointmentReportDTO> appointments = appointmentDAO.getAppointmentsWithASpecificTimeIntervalByEstateId(from, to, estateId);
        Object[][] result = setHeader(headers, appointments.size());

        int rows = appointments.size();

        for (AppointmentReportDTO appointmentReportDTO : appointments) {
            for (int i = 1; i <= rows; i++) {
                result[i][0] = appointmentReportDTO.getStart();
                result[i][1] = appointmentReportDTO.getEnd();
                result[i][2] = appointmentReportDTO.getEmail();
                result[i][3] = appointmentReportDTO.getFirstName();
                result[i][4] = appointmentReportDTO.getLastName();
                result[i][5] = appointmentReportDTO.getPhoneNumber();
                result[i][6] = appointmentReportDTO.getAppointmentStatus();
            }
        }

        return result;
    }

    private Object[][] setHeader(String[] values, int arrL) {
        Object[][] headers = new Object[arrL + 1][values.length];

        for (int i = 0; i < values.length; i++) {
            headers[0][i] = values[i];
        }

        return headers;
    }

}
