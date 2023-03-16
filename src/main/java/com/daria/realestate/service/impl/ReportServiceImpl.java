package com.daria.realestate.service.impl;

import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.domain.Report;
import com.daria.realestate.dto.Page;
import com.daria.realestate.dto.enums.FileLocation;
import com.daria.realestate.service.DriveService;
import com.daria.realestate.service.PrepExcelReportService;
import com.daria.realestate.service.ReportService;
import com.daria.realestate.service.report.FileInstanceServiceFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final FileInstanceServiceFactory fileInstanceServiceFactory;
    private final ReportDAO reportDAO;
    private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;
    private final PrepExcelReportService prepExcelReportService;
    private final DriveService driveService;


    public ReportServiceImpl(FileInstanceServiceFactory fileInstanceServiceFactory, ReportDAO reportDAO, DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO, PrepExcelReportService prepExcelReportService, DriveService driveService) {
        this.fileInstanceServiceFactory = fileInstanceServiceFactory;
        this.reportDAO = reportDAO;
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
        this.prepExcelReportService = prepExcelReportService;
        this.driveService = driveService;
    }

    @Override
    public String generateReport(LocalDateTime from, LocalDateTime to, long estateId) {
        FileLocation location = FileLocation.valueOf(dynamicApplicationConfigurationDAO.getByConfigNameAndStatus("fileSettings", true).getConfigType());

        String filePath = prepExcelReportService.generateLocalFileName(estateId);
        String fileName = fileInstanceServiceFactory.createInstance(location).saveFile(filePath, prepExcelReportService.generateReport(from, to, estateId));

        if(reportDAO.getById(estateId) == null){
            reportDAO.create(new Report(estateId, fileName, LocalDateTime.now(),location));
        }else{
            reportDAO.update(new Report(estateId, fileName, LocalDateTime.now(),location));
        }

        return fileName;
    }

    @Override
    public File getReport(long estateId) {
        Report report = reportDAO.getById(estateId);
        return driveService.download(estateId, report.getFilePath());
    }
    @Override
    public Page<Report> getAllReportsOfAUser(long userId, int pageSize, int pageNumber){
        List<Report> reports = reportDAO.getAllReportsOfAUser(userId, new PaginationFilter(pageNumber, pageSize));
        return new Page<>(reports, reportDAO.countAllReportsOfAUser(userId), pageNumber, pageSize);
    }
}
