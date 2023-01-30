package com.daria.realestate.controller;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.service.AppointmentService;
import com.daria.realestate.service.impl.AppointmentServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create/{userId}")
    public Appointment createAppointment(@RequestBody Appointment appointment, @PathVariable long userId) {
        return appointmentService.createAppointment(appointment, userId);
    }

    @GetMapping("/details/{appointmentId}")
    public Appointment getAppointmentDetails(@PathVariable long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    @PutMapping("/update/{appointmentId}")
    public Appointment updateAppointment(@RequestBody Appointment appointment, @PathVariable long appointmentId) {
        return appointmentService.updateAppointment(appointmentId, appointment);
    }
}
