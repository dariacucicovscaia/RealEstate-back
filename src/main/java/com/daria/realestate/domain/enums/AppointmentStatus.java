package com.daria.realestate.domain.enums;

public enum AppointmentStatus {
    /**
     * The appointment is planned for a specific date and time
     */
    SCHEDULED,
    /**
     * The appointment passed successfully
     */
    FINISHED,
    /**
     * The appointment has been scheduled and confirmed
     */
    CONFIRMED,
    /**
     * The appointment can be canceled by the user
     */
    CANCELED,
    /**
     * The appointment can be denied by the owner of the estate
     */
    DENIED,
    /**
     * If there are overlays the appointment will be rejected
     */
    REJECTED
}
