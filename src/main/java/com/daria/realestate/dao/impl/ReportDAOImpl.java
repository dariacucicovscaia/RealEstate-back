package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.dao.mappers.ReportMapper;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.Report;
import com.daria.realestate.dto.enums.FileLocation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ReportDAOImpl extends AbstractDAOImpl<Report> implements ReportDAO {
    private final static String SQL_INSERT_REPORT = " insert into report(estate_id, file_path,  last_updated, location) values ( ? , ? , ? , ? )";
    private final static String SQL_REMOVE_REPORT = " delete from report where estate_id = ? ";
    private final static String SQL_GET_REPORT_BY_ID_AND_LOCATION = " SELECT * FROM report WHERE estate_id = ? and location = ? ";
    private final static String SQL_GET_REPORT_BY_ID = " SELECT * FROM report WHERE estate_id = ?  ";
    private final static String SQL_UPDATE_REPORT = " update report set file_path = ? , last_updated = ? , location = ? where estate_id = ?  ";

    protected ReportDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public Report create(Report report) {
        getJdbcTemplate().update(SQL_INSERT_REPORT, report.getEstateId(), report.getFilePath(), report.getLastUpdated().toString(), report.getLocation().toString());

        return getByIdAndLocation(report.getEstateId(), report.getLocation());
    }

    @Override
    public Report update(Report report) {
        getJdbcTemplate()
                .update(SQL_UPDATE_REPORT,
                        report.getFilePath(),
                        report.getLastUpdated(),
                        report.getLocation().toString(),
                        report.getEstateId()
                );

        return getById(report.getEstateId());
    }

    @Override
    public int removeById(long id) {
        return getJdbcTemplate().update(SQL_REMOVE_REPORT, id);
    }

    @Override
    public Report getById(long id) {
        try {
            return getJdbcTemplate().queryForObject(SQL_GET_REPORT_BY_ID, new ReportMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Report getByIdAndLocation(long id, FileLocation location) {
        return getJdbcTemplate().queryForObject(SQL_GET_REPORT_BY_ID_AND_LOCATION, new ReportMapper(), id, location.toString());
    }

    @Override
    public List<Report> getAllReportsOfAUser(long userId, PaginationFilter paginationFilter) {
        String sql = " select r.* from `estate` as e  " +
                " inner join `user` as u on e.owner_id = u.id " +
                " inner join `report` as r on r.estate_id = e.id " +
                " where u.id = ? limit ? offset ? ";

        return getJdbcTemplate().query(
                sql,
                new ReportMapper(),
                userId,
                paginationFilter.getNrOfElementsWeWantDisplayed(),
                getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed())
        );
    }

    @Override
    public Integer countAllReportsOfAUser(long userId) {
        String sql = " select count(*) from `estate` as e  " +
                " inner join `user` as u on e.owner_id = u.id " +
                " inner join `report` as r on r.estate_id = e.id " +
                " where u.id = ? ";

        return getJdbcTemplate().queryForObject(
                sql,
                Integer.class,
                userId
        );
    }
}
