package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.impl.AppointmentDAOImpl;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dto.AppointmentReportDTO;
import com.daria.realestate.service.PrepExcelObjectService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class ExcelReportDataServiceImplTest {
   @Mock
    private AppointmentDAO appointmentDAO;
    private PrepExcelObjectService serviceUnderTest;

    @Before
    public void before() {
        appointmentDAO = mock(AppointmentDAOImpl.class);

        serviceUnderTest = new PrepExcelObjectServiceImpl((AppointmentDAOImpl) appointmentDAO);
    }

    @Test
    public void test() {
        long estateId = 1;
        LocalDateTime from = LocalDateTime.parse("2023-01-05T14:45:29.146130800");
        LocalDateTime to = LocalDateTime.parse("2023-01-25T14:45:29.147128400");

        List<AppointmentReportDTO> appointmentReportList = new ArrayList<>();
        appointmentReportList.add(new AppointmentReportDTO(from, to, "email", "firstName", "lastName", "phoneNumber", AppointmentStatus.CONFIRMED));
        appointmentReportList.add(new AppointmentReportDTO(from, to, "email", "firstName", "lastName", "phoneNumber", AppointmentStatus.CONFIRMED));
        appointmentReportList.add(new AppointmentReportDTO(from, to, "email", "firstName", "lastName", "phoneNumber", AppointmentStatus.CONFIRMED));
        appointmentReportList.add(new AppointmentReportDTO(from, to, "email", "firstName", "lastName", "phoneNumber", AppointmentStatus.CONFIRMED));

        when(appointmentDAO.getAppointmentsWithASpecificTimeIntervalByEstateId(from, to, estateId)).thenReturn(appointmentReportList);

        Object[][] result = serviceUnderTest.prepareReportDataForEstateAppointments(from, to, estateId);

        verify(appointmentDAO).getAppointmentsWithASpecificTimeIntervalByEstateId(from, to, estateId);
        Assert.assertNotNull(result);
    }
}