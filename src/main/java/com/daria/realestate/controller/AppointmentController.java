package com.daria.realestate.controller;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.enums.AppointmentStatus;
import com.daria.realestate.dto.AppointmentDTO;
import com.daria.realestate.dto.Page;
import com.daria.realestate.service.AppointmentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/create/{startTime}/{userId}/{estateId}")
    public AppointmentDTO createAppointment(@PathVariable String startTime, @PathVariable long userId, @PathVariable long estateId) {
        return appointmentService.createAppointment(startTime, userId, estateId);
    }

    @GetMapping("/details/{appointmentId}")
    public Appointment getAppointmentDetails(@PathVariable long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment, @PathVariable long appointmentId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(appointmentService.updateAppointment(appointmentId, appointment));
    }
    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/update/confirm-status/{appointmentId}")
    public ResponseEntity<AppointmentStatus> updateAppointmentStatus( @PathVariable long appointmentId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(appointmentService.confirmAppointment(appointmentId));
    }

    @GetMapping("/myAppointments/{userId}")
    public Page<Appointment> getMyAppointments(@PathVariable long userId, @RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        return appointmentService.getAppointmentsOfAUser(userId, page, size);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/myAppointments/{userId}/{appointmentStatus}")
    public Page<Appointment> usersAppointmentsByAppointmentStatus(@PathVariable long userId, @PathVariable AppointmentStatus appointmentStatus, @RequestParam("page") int page,
                                                                  @RequestParam("size") int size) {
        return appointmentService.usersAppointmentsByAppointmentStatus(userId,appointmentStatus, page, size);
    }
}
