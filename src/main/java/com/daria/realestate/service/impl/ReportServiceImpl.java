package com.daria.realestate.service.impl;

import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.dto.enums.FileLocation;
import com.daria.realestate.service.PrepExcelReportService;
import com.daria.realestate.service.ReportService;
import com.daria.realestate.service.report.FileServiceFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class ReportServiceImpl implements ReportService {
    private final FileServiceFactory fileServiceFactory;
    private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;
    private final PrepExcelReportService prepExcelReportService;


    public ReportServiceImpl(FileServiceFactory fileServiceFactory, DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO, PrepExcelReportService prepExcelReportService) {
        this.fileServiceFactory = fileServiceFactory;
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
        this.prepExcelReportService = prepExcelReportService;
    }

    @Override
    public String generateReport(LocalDateTime from, LocalDateTime to, long estateId) {
        FileLocation location = FileLocation.valueOf(dynamicApplicationConfigurationDAO.getByConfigNameAndStatus("fileSettings", "active").getConfigType());

        String filePath = prepExcelReportService.generateLocalFileName(estateId);
        String fileName = fileServiceFactory.createInstance(location).saveFile(filePath, prepExcelReportService.generateReport(from, to, estateId));

        return fileName;
    }


    @Override
    public File getReport(long estateId, String simpleName) {
        FileLocation location = FileLocation.valueOf(dynamicApplicationConfigurationDAO.getByConfigNameAndStatus("fileSettings", "active").getConfigType());

        return fileServiceFactory.createInstance(location).getFile(estateId, simpleName);
    }

}
