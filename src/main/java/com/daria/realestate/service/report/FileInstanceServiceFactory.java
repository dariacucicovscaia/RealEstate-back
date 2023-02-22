package com.daria.realestate.service.report;

import com.daria.realestate.dto.enums.FileLocation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FileInstanceServiceFactory {
    private FileOperations localReportService;

    private FileOperations driveService;

    public FileInstanceServiceFactory(@Qualifier("localReportServiceImpl") FileOperations localReportService, @Qualifier("driveServiceImpl") FileOperations driveService) {
        this.localReportService = localReportService;
        this.driveService = driveService;
    }

    public FileOperations createInstance(FileLocation location) {
        if (FileLocation.local.equals(location)) {
            return localReportService;
        } else if (FileLocation.drive.equals(location)) {
            return driveService;
        } else return null;
    }
}
