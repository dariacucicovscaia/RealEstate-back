package com.daria.realestate.dao;

import com.daria.realestate.domain.Appointment;
import com.daria.realestate.domain.User;

public interface UserDAO extends AbstractDAO<User> {
    /**
     * Inserts a user into the db
     *
     * @param user to be inserted
     * @return user with the generated id
     */
    User insertUser(User user);

    /**
     * Updates the user
     *
     * @param user to be updated, with the new fields
     * @return updated User
     */
    User updateUser(User user);

    /**
     * Gets the user by email
     *
     * @param email the search parameter
     * @return user that has this email
     */
    User getUserByEmail(String email);

    /**
     * Removes user by email
     * @param email email to be used by removing
     * @return id of the removed User
     */
    long removeByEmail(String email);
}