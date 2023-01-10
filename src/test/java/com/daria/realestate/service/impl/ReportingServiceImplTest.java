package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.impl.AppointmentDAOImpl;
import com.daria.realestate.domain.User;
import com.daria.realestate.dto.AppointmentDTO;
import com.daria.realestate.service.ReportingService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportingServiceImplTest {
    @Mock
    private final AppointmentDAO appointmentDAO;

    private final ReportingService serviceUnderTest;

    public ReportingServiceImplTest() {
        this.appointmentDAO = mock(AppointmentDAOImpl.class);
        this.serviceUnderTest = new ReportingServiceImpl((AppointmentDAOImpl) appointmentDAO);
    }

    @Test
    public void testCreationOfAReport() {
        List<AppointmentDTO> appointmentsToBeInserted = new ArrayList<>();

        appointmentsToBeInserted.add(sampleDataAppointmentDTO());
        appointmentsToBeInserted.add(sampleDataAppointmentDTO());
        appointmentsToBeInserted.add(sampleDataAppointmentDTO());
        appointmentsToBeInserted.add(sampleDataAppointmentDTO());
        appointmentsToBeInserted.add(sampleDataAppointmentDTO());


        when(appointmentDAO.getAppointmentsWithASpecificTimeInterval(
                LocalDateTime.parse("2023-01-05T14:45:29.146130800"),
                LocalDateTime.parse("2023-01-25T14:45:29.147128400"), "email")).thenReturn(appointmentsToBeInserted);

        List<User> users = new ArrayList<>();

        users.add(new User("email", "password"));

        Boolean successful = serviceUnderTest.createAppointmentReportWithTimeInterval(
                LocalDateTime.parse("2023-01-05T14:45:29.146130800"),
                LocalDateTime.parse("2023-01-25T14:45:29.147128400"), users);
        if (successful) {
            List<AppointmentDTO> fetchedListFromExcel = null;
            try {
                fetchedListFromExcel = serviceUnderTest.readDataOfAUserFromExcelFile("email");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Assert.assertEquals(fetchedListFromExcel.size(), appointmentsToBeInserted.size());
            for (int i = 0; i < fetchedListFromExcel.size(); i++) {
                Assert.assertEquals(appointmentsToBeInserted.get(i).getEmail(), fetchedListFromExcel.get(i).getEmail());
                Assert.assertEquals(appointmentsToBeInserted.get(i).getFirstName(), fetchedListFromExcel.get(i).getFirstName());
                Assert.assertEquals(appointmentsToBeInserted.get(i).getLastName(), fetchedListFromExcel.get(i).getLastName());
                Assert.assertEquals(appointmentsToBeInserted.get(i).getPhoneNumber(), fetchedListFromExcel.get(i).getPhoneNumber());
                Assert.assertEquals(appointmentsToBeInserted.get(i).getStart(), fetchedListFromExcel.get(i).getStart());
                Assert.assertEquals(appointmentsToBeInserted.get(i).getEnd(), fetchedListFromExcel.get(i).getEnd());
                Assert.assertEquals(appointmentsToBeInserted.get(i).getEstateId(), fetchedListFromExcel.get(i).getEstateId());
            }
        }


    }

    private AppointmentDTO sampleDataAppointmentDTO() {
        return new AppointmentDTO(
                "email",
                "firstName",
                "lastName",
                "phoneNumber",
                LocalDateTime.parse("2023-01-05T14:45:29.146130800"),
                LocalDateTime.parse("2023-01-25T14:45:29.147128400"),
                1L
        );
    }
}