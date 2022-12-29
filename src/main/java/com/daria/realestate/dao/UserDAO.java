package com.daria.realestate.dao;

import com.daria.realestate.domain.User;

public interface UserDAO extends DAO<User> {

    /**
     * Method used for entity update
     *
     * @param user to be updated
     * @return updated user
     */
    User update(User user);

    /**
     * Gets the user by email
     *
     * @param email the search parameter
     * @return user that has this email
     */
    User getUserByEmail(String email);

    /**
     * Removes user by email
     *
     * @param email email to be used by removing
     * @return id of the removed User
     */
    long removeByEmail(String email);


}