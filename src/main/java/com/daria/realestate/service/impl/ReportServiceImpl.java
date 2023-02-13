package com.daria.realestate.service.impl;


import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.domain.enums.FileLocation;
import com.daria.realestate.service.ReportService;
import com.daria.realestate.service.factory.FileServiceFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class ReportServiceImpl implements ReportService {
    private final FileServiceFactory fileServiceFactory;

    private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;


    public ReportServiceImpl(FileServiceFactory fileServiceFactory, DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO) {
        this.fileServiceFactory = fileServiceFactory;
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
    }

    @Override
    public String generateReport(LocalDateTime from, LocalDateTime to, long estateId) {
        FileLocation location = FileLocation.valueOf(dynamicApplicationConfigurationDAO.getByConfigNameAndStatus("fileSettings", "active").getConfigType());
        return fileServiceFactory.createInstance(location).saveFile(from, to, estateId);
    }


    @Override
    public File getReport(long estateId, String simpleName, FileLocation location) {
        return fileServiceFactory.createInstance(location).getFile(estateId, simpleName);
    }

}
