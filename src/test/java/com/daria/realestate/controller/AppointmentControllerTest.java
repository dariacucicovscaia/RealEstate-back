package com.daria.realestate.controller;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.Estate;
import com.daria.realestate.dto.Page;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.service.impl.AppointmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AppointmentController.class)
public class AppointmentControllerTest {

    @MockBean
    private AppointmentServiceImpl appointmentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAppointment() throws Exception {
        given(appointmentService.createAppointment(testDataAppointment(), 1L)).willReturn(testDataAppointment());

        this.mockMvc.perform(post("/api/v1/appointment/create/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"madeAt\" : \"2022-02-01T12:02:12\",\n" +
                                "    \"start\" : \"2022-02-08T12:02:12\",\n" +
                                "    \"end\" : \"2022-02-08T13:02:12\",\n" +
                                "    \"appointmentStatus\" : \"DENIED\",\n" +
                                "    \"estate\" : {\n" +
                                "         \"id\" : 1,\n" +
                                "         \"paymentTransactionType\" : \"RENT\",\n" +
                                "         \"acquisitionStatus\" : \"OPEN\",\n" +
                                "         \"createdAt\" :\"2017-05-15T15:12:59.152Z\", \n" +
                                "         \"lastUpdatedAt\" : \"2017-05-15T15:12:59.152Z\"\n" +
                                "    }\n" +
                                "\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createAppointment"));
    }

    @Test
    public void getAppointmentDetails() throws Exception {
        given(appointmentService.getAppointmentById(1L)).willReturn(testDataAppointment());

        this.mockMvc.perform(get("/api/v1/appointment/details/{appointmentId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.madeAt", is("2022-02-01T12:02:12")))
                .andDo(print());
    }

    @Test
    public void updateAppointment() throws Exception {
        when(appointmentService.updateAppointment(1L, testDataAppointment())).thenReturn(testDataAppointment());

        this.mockMvc.perform(put("/api/v1/appointment/update/{appointmentId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"madeAt\" : \"2022-02-01T12:02:12\",\n" +
                                "    \"start\" : \"2022-02-08T12:02:12\",\n" +
                                "    \"end\" : \"2022-02-08T13:02:12\",\n" +
                                "    \"appointmentStatus\" : \"DENIED\",\n" +
                                "    \"estate\" : {\n" +
                                "         \"id\" : 1,\n" +
                                "         \"paymentTransactionType\" : \"RENT\",\n" +
                                "         \"acquisitionStatus\" : \"OPEN\",\n" +
                                "         \"createdAt\" :\"2017-05-15T15:12:59.152Z\", \n" +
                                "         \"lastUpdatedAt\" : \"2017-05-15T15:12:59.152Z\"\n" +
                                "    }\n" +
                                "\n" +
                                "}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(handler().methodName("updateAppointment"));
    }
    @Test
    public void getMyAppointments() throws Exception {
        given(appointmentService.getAppointmentsOfAUser(1L, 1, 5)).willReturn(testDataPageableAppointment());

        this.mockMvc.perform(get("/api/v1/appointment/myAppointments/{userId}?page=1&size=5", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(handler().methodName("getMyAppointments"));
    }

    @Test
    public void usersAppointmentsByAppointmentStatus() throws Exception {
        given(appointmentService.usersAppointmentsByAppointmentStatus(1L, AppointmentStatus.DENIED, 1, 5)).willReturn(testDataPageableAppointment());

        this.mockMvc.perform(get("/api/v1/appointment/myAppointments/{userId}/{appointmentStatus}?page=1&size=5", 1, "DENIED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].appointmentStatus", is("DENIED")))
                .andDo(print());
    }

    private Page<Appointment> testDataPageableAppointment() {
        List<Appointment> content = new ArrayList<>();
        content.add(testDataAppointment());
        content.add(testDataAppointment());
        content.add(testDataAppointment());
        content.add(testDataAppointment());
        content.add(testDataAppointment());

        return new Page(content, content.size(), 1, content.size());
    }

    private Appointment testDataAppointment() {
        return new Appointment(
                LocalDateTime.parse("2022-02-01T12:02:12"),
                LocalDateTime.parse("2022-02-08T12:02:12"),
                LocalDateTime.parse("2022-02-08T13:02:12"),
                AppointmentStatus.DENIED,
                new Estate(
                        1L,
                        PaymentTransactionType.RENT.toString(),
                        AcquisitionStatus.OPEN.toString(),
                        LocalDateTime.parse("2017-05-15T15:12:59.152"),
                        LocalDateTime.parse("2017-05-15T15:12:59.152")
                )
        );
    }
}