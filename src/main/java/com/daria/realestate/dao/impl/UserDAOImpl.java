package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.mappers.UserMapper;
import com.daria.realestate.domain.User;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {
    private static final String SQL_CREATE_USER = " insert into `user` (email, password) VALUES( ? , ? ) ";
    private static final String SQL_GET_USER_BY_ID = " select * from `user` where id = ? ";
    private static final String SQL_DELETE_USER_BY_ID = " delete from `user` where  id = ? ";
    private static final String SQL_GET_USER_BY_EMAIL = " select * from `user` where email = ? ";
    private static final String SQL_DELETE_USER_BY_EMAIL = " delete from `user` where email = ? ";
    private static final String SQL_GET_ALL_USERS_THAT_HAVE_APPOINTMENTS = " select distinct u.* from `user` as u " + " inner join `user_appointment` as ua on ua.user_id = u.id ";
    private static final String SQL_GET_ALL_USERS_OF_AN_APPOINTMENT = " select distinct u.* from `user` as u " + " inner join `user_appointment` as ua on ua.user_id = u.id where ua.appointment_id = ? ";
    private static final String SQL_GET_ESTATE_OWNER = " select u.* from `user` as u " + " inner join `estate` as e on e.owner_id = u.id " + " where e.id = ?";
    private static final String SQL_UPDATE_USER = " update `user` set email = ?, password = ? where id = ? ";

    public UserDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate()
                .update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getPassword());
                    return ps;
                }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public User update(User user) {
        getJdbcTemplate().update(SQL_UPDATE_USER, user.getEmail(), user.getPassword(), user.getId());
        return getById(user.getId());
    }

    @Override
    public User getById(long id) {
        return getJdbcTemplate().queryForObject(SQL_GET_USER_BY_ID, new UserMapper(), id);
    }

    @Override
    public User getUserByEmail(String email) {
        return getJdbcTemplate().queryForObject(SQL_GET_USER_BY_EMAIL, new UserMapper(), email);
    }

    @Override
    public User getOwnerOfAnEstate(long estateId) {
        return getJdbcTemplate().queryForObject(SQL_GET_ESTATE_OWNER, new UserMapper(), estateId);
    }

    @Override
    public List<User> getAllUsersThatHaveAppointments() {
        return getJdbcTemplate().query(SQL_GET_ALL_USERS_THAT_HAVE_APPOINTMENTS, new UserMapper());
    }

    @Override
    public List<User> getAllUsersOfAnAppointment(long appointmentId) {
        return getJdbcTemplate().query(SQL_GET_ALL_USERS_OF_AN_APPOINTMENT, new UserMapper(), appointmentId);
    }

    @Override
    public int removeById(long id) {
        return getJdbcTemplate().update(SQL_DELETE_USER_BY_ID, id);
    }

    @Override
    public int removeByEmail(String email) {
        return getJdbcTemplate().update(SQL_DELETE_USER_BY_EMAIL, email);
    }
}
