package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Appointment {
    private Long id;
    private LocalDateTime madeAt;
    private LocalDateTime start;
    private LocalDateTime end;
    private AppointmentStatus appointmentStatus;

    private Estate estate;
    private List<User> users;

    public Appointment() {
    }

    public Appointment(LocalDateTime madeAt, LocalDateTime start, LocalDateTime end, AppointmentStatus appointmentStatus, long estateId) {
        this.madeAt = madeAt;
        this.start = start;
        this.end = end;
        this.appointmentStatus = appointmentStatus;
        setEstateId(estateId);
    }

    public Appointment(Long id, LocalDateTime madeAt, LocalDateTime start, LocalDateTime end, String appointmentStatus, long estateId) {
        this.id = id;
        ;
        this.madeAt = madeAt;
        this.start = start;
        this.end = end;
        this.appointmentStatus = AppointmentStatus.valueOf(appointmentStatus);
        setEstateId(estateId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getMadeAt() {
        return madeAt;
    }

    public void setMadeAt(LocalDateTime madeAt) {
        this.madeAt = madeAt;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstateId(long id) {
        this.estate = new Estate();
        this.estate.setId(id);
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", madeAt=" + madeAt +
                ", start=" + start +
                ", end=" + end +
                ", appointmentStatus=" + appointmentStatus +
                ", estate=" + estate +
                ", users=" + users +
                '}';
    }
}
