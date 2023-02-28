package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserRoleDAO;
import com.daria.realestate.dao.mappers.RoleMapper;
import com.daria.realestate.domain.enums.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class UserRoleDAOImpl implements UserRoleDAO {
    private static final String SQL_INSERT_USER_ROLE = " insert into user_role (user_id, role) values(?, ?) ";
    private static final String SQL_REMOVE_USER_ROLE = " delete from user_role where user_id = ? and role = ? ";
    private static final String SQL_SELECT_ALL_ROLES_OF_A_USER = " select ur.role from user_role as ur where ur.user_id = ? ";

    private final JdbcTemplate jdbcTemplate;

    protected UserRoleDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Role create(long userId, Role role) {
        int rowsAffected = jdbcTemplate.update(SQL_INSERT_USER_ROLE, userId, role.toString());
        if (rowsAffected != 0) {
            return role;
        } else {
            return null;
        }
    }

    @Override
    public int removeRole(long userId, Role role) {
        return jdbcTemplate.update(SQL_REMOVE_USER_ROLE, userId, role.toString());
    }

    @Override
    public List<Role> getRolesOfAUser(long userId) {
        return jdbcTemplate.query(SQL_SELECT_ALL_ROLES_OF_A_USER, new RoleMapper(), userId);
    }
}
