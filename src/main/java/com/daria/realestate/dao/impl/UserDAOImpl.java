package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.UserDAO;
import com.daria.realestate.dao.mappers.UserMapper;
import com.daria.realestate.dao.mappers.UserWithAllProfileDetailsMapper;
import com.daria.realestate.dao.mappers.UserWithNoPasswordMapper;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.User;
import com.daria.realestate.dto.UserWithAllProfileDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {
    private static final String SQL_CREATE_USER = " insert into `user` (email, password, account_status, created_at ) VALUES( ? , ? , ? , ? ) ";
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
                    ps.setBoolean(3, user.isAccountActive());
                    ps.setTimestamp(4, Timestamp.valueOf(user.getCreatedAt()));
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
        return getJdbcTemplate().queryForObject(SQL_GET_USER_BY_ID, new UserWithNoPasswordMapper(), id);
    }

    @Override
    public User getUserByEmail(String email) {
        try{
            return getJdbcTemplate().queryForObject(SQL_GET_USER_BY_EMAIL, new UserMapper(), email);
        }catch (IncorrectResultSizeDataAccessException e){
            throw new RuntimeException("Such user could not be found, check email spelling and try again");
        }
    }

    @Override
    public User getOwnerOfAnEstate(long estateId) {
        return getJdbcTemplate().queryForObject(SQL_GET_ESTATE_OWNER, new UserWithNoPasswordMapper(), estateId);
    }

    @Override
    public List<User> getAllUsersThatHaveAppointments() {
        return getJdbcTemplate().query(SQL_GET_ALL_USERS_THAT_HAVE_APPOINTMENTS, new UserWithNoPasswordMapper());
    }

    @Override
    public List<UserWithAllProfileDetails> getAllUsers(String criteria, PaginationFilter paginationFilter) {
        String SQL = " select u.id, p.first_name, p.last_name, u.email, u.account_status, u.created_at, p.profile_picture from `user` as u " + " inner join `profile` as p on u.id = p.user_id ";
        if (StringUtils.isNotBlank(criteria)) {
            SQL += " where  LCASE(first_name) LIKE '%" + criteria.toLowerCase() + "%' or  LCASE(last_name) LIKE '%" + criteria.toLowerCase() + "%' or  LCASE(email) LIKE '%" + criteria.toLowerCase() + "%' ";
        }
        SQL += " limit ?  offset ? ";
        return getJdbcTemplate().query(
                SQL,
                new UserWithAllProfileDetailsMapper(),
                paginationFilter.getNrOfElementsWeWantDisplayed(),
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()));
    }


    @Override
    public Integer getAllUsersCount(String criteria) {
        String SQL = "select count(*)  from `user` as u inner join `profile` as p on u.id = p.user_id ";
        if (StringUtils.isNotBlank(criteria)) {
            SQL = SQL + " where  LCASE(first_name) LIKE '%" + criteria + "%' or  LCASE(last_name) LIKE '%" + criteria + "%' or  LCASE(email) LIKE '%" + criteria + "%' or  LCASE(first_name) LIKE '%" + criteria + "%'";
        }
        return getJdbcTemplate().queryForObject(SQL, Integer.class);
    }

    @Override
    public User updateProfileStatus(long userId, boolean isActive) {
        String SQL = " update `user` set account_status = ? where id = ? ";
        getJdbcTemplate().update(SQL, isActive, userId);

        return getById(userId);
    }

    @Override
    public UserWithAllProfileDetails getUserWithAllProfileDetails(long userId) {
        String SQL = " select u.id, p.first_name, p.last_name, u.email, u.account_status, u.created_at, p.profile_picture from `user` as u " +
                "inner join `profile` as p on u.id = p.user_id  where u.id = ?";

        return getJdbcTemplate().queryForObject(  SQL,
                new UserWithAllProfileDetailsMapper(), userId);
    }

    @Override
    public List<User> getAllUsersOfAnAppointment(long appointmentId) {
        return getJdbcTemplate().query(SQL_GET_ALL_USERS_OF_AN_APPOINTMENT, new UserWithNoPasswordMapper(), appointmentId);
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
