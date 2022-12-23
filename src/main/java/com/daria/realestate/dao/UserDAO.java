package com.daria.realestate.dao;


import com.daria.realestate.domain.PaginationFilter;

import java.util.List;

public interface UserDAO<User> extends GenericDAO<User> {
    /**
     * Gets users created between two time slots
     *
     * @param startingDateTime starting time slot
     * @param endingDateTime   ending time slot
     * @param paginationFilter filter for using pagination
     * @return list of all users between two time slots
     */
    List<com.daria.realestate.domain.User> getUsersCreatedBetweenTwoTimeSlots(String startingDateTime, String endingDateTime, PaginationFilter paginationFilter);
}