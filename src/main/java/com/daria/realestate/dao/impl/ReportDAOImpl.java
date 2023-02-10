package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.dao.mappers.ReportMapper;
import com.daria.realestate.domain.Report;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository
public class ReportDAOImpl extends AbstractDAOImpl<Report> implements ReportDAO {

    private final static String SQL_INSERT_REPORT = " insert into report(estate_id, drive_file_id, local_file_path,  last_updated, location) values ( ?, ? , ? , ? , ? )";
    private final static String SQL_REMOVE_REPORT = " delete from report where estate_id = ? ";
    private final static String SQL_GET_REPORT_BY_ID = " SELECT * FROM report WHERE estate_id = ? ";
    private final static String SQL_UPDATE_REPORT = " update report set drive_file_id = ? , last_updated = ? , location = ? where estate_id = ?  ";

    protected ReportDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public Report create(Report report) {
        getJdbcTemplate().update(SQL_INSERT_REPORT,report.getEstateId(), report.getDriveFileId(), report.getLocalFilePath(), report.getLastUpdated().toString(), report.getLocation().toString() );

        return getById(report.getEstateId());
    }

    @Override
    public Report update(Report report) {

        getJdbcTemplate()
                .update(SQL_UPDATE_REPORT,
                        report.getDriveFileId(),
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
        return getJdbcTemplate().queryForObject(SQL_GET_REPORT_BY_ID, new ReportMapper(), id);
    }


}
