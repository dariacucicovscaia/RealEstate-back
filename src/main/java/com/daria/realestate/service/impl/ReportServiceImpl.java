package com.daria.realestate.service.impl;


import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.domain.enums.FileLocation;
import com.daria.realestate.service.factory.FileOperations;
import com.daria.realestate.service.ReportService;
import com.daria.realestate.service.factory.FileServiceFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class ReportServiceImpl implements ReportService {
    private final FileServiceFactory fileServiceFactory;


    public ReportServiceImpl(FileServiceFactory fileServiceFactory) {
        this.fileServiceFactory = fileServiceFactory;
    }

    @Override
    public String generateReport(LocalDateTime from, LocalDateTime to, long estateId, FileLocation location) {
        return fileServiceFactory.createInstance(location).saveFile(from, to, estateId);
    }


    @Override
    public File getReport(long estateId, String simpleName, FileLocation location) {
        return fileServiceFactory.createInstance(location).getFile(estateId, simpleName);
    }

}
