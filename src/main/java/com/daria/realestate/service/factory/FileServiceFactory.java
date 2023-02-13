package com.daria.realestate.service.factory;

import com.daria.realestate.domain.enums.FileLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Service
public class FileServiceFactory {
    private LocalReportServiceImpl localReportService;
    private DriveReportServiceImpl driveService;

    @Autowired
    public void setLocalReportService(LocalReportServiceImpl localReportService) {
        this.localReportService = localReportService;
    }

    @Autowired
    public void setDriveService(DriveReportServiceImpl driveService) {
        this.driveService = driveService;
    }

    private static final Map<FileLocation, FileOperations> fileOperationsMap = new HashMap<>();

    @PostConstruct
    private Map<FileLocation, FileOperations> getObject() {
        fileOperationsMap.put(FileLocation.local, localReportService);
        fileOperationsMap.put(FileLocation.drive, driveService);
        return fileOperationsMap;
    }

    public FileOperations createInstance(FileLocation location) {
        return fileOperationsMap.get(location);
    }
}
