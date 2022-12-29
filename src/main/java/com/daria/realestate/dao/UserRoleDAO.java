package com.daria.realestate.dao;

import com.daria.realestate.domain.User;
import com.daria.realestate.domain.UserRole;
import com.daria.realestate.domain.enums.Roles;

import java.util.List;

public interface UserRoleDAO extends DAO<UserRole> {
    /**
     * Get all roles of a user
     *
     * @param user
     * @return list of roles
     */
    List<Roles> getRolesOfAUser(User user);
}
