package com.daria.realestate.service.impl;

import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.domain.Report;
import com.daria.realestate.domain.enums.FileLocation;
import com.daria.realestate.service.DriveService;
import com.daria.realestate.service.LocalReportService;
import com.daria.realestate.service.ReportService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportDAO reportDAO;
    private final DriveService driveService;
    private final LocalReportService localReportService;

    public ReportServiceImpl(ReportDAO reportDAO, DriveService driveService, LocalReportService localReportService) {
        this.reportDAO = reportDAO;
        this.driveService = driveService;
        this.localReportService = localReportService;
    }

    @Override
    public String generateLocalReport(LocalDateTime from, LocalDateTime to, long estateId, String estateAddress) {
        return localReportService.generateReport(from, to, estateId, estateAddress);
    }

    @Override
    public String uploadReportToDrive(long estateId, String fileName, File fileToInsert) {
        String driveFileId = driveService.uploadFileIntoDrive(fileName, fileToInsert);
        reportDAO.update(new Report(estateId, driveFileId, LocalDateTime.now(), FileLocation.DRIVE));
        return driveFileId;
    }

    @Override
    public File downloadReportFromDrive(long estateId, String fileNameFromDrive) {
        Report report = reportDAO.getById(estateId);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(report.getLocalFilePath());
            driveService.downloadFile(fileNameFromDrive).writeTo(fileOutputStream);

            reportDAO.update(new Report(estateId, driveService.getFileByName(fileNameFromDrive).getId(), report.getLocalFilePath(), LocalDateTime.now(), FileLocation.DRIVE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new File(report.getLocalFilePath());
    }

}
