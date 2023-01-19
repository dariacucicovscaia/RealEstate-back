package com.daria.realestate.dao;

import com.daria.realestate.domain.enums.Role;

import java.util.List;

public interface UserRoleDAO {
    /**
     * Get all roles of a user
     *
     * @param userId
     * @return list of roles
     */
    List<Role> getRolesOfAUser(long userId);

    /**
     *
     * @param userId
     * @param role
     * @return rowsAffected
     */
    int removeRole(long userId, Role role);
    Role create(long userId, Role role);

}
