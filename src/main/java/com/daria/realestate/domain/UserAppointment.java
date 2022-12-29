package com.daria.realestate.domain;


public class UserAppointment {
    private long id;
    private User user;
    private Appointment appointment;

    public UserAppointment(long id) {
        this.id = id;
    }

    public UserAppointment(User user, Appointment appointment) {
        this.user = user;
        this.appointment = appointment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}