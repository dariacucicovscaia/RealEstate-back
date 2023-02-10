package com.daria.realestate.service.factory;

import com.daria.realestate.domain.enums.FileLocation;
import org.springframework.stereotype.Service;

@Service
public class FileServiceFactory {
    private final LocalReportServiceImpl localReportService;
    private final DriveReportServiceImpl driveService;

    public FileServiceFactory(LocalReportServiceImpl localReportService, DriveReportServiceImpl driveService) {
        this.localReportService = localReportService;
        this.driveService = driveService;
    }

    public FileOperations createInstance(FileLocation location) {
        if (location.equals(FileLocation.LOCAL)) {
            return localReportService;
        } else if (location.equals(FileLocation.DRIVE)) {
            return driveService;
        } else
            return null;
    }
}
