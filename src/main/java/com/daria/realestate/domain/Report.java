package com.daria.realestate.domain;

import com.daria.realestate.dto.enums.FileLocation;

import java.time.LocalDateTime;

public class Report {
    private long estateId;
    private String filePath;
    private LocalDateTime lastUpdated;
    private FileLocation location;


    public Report(long estateId, String filePath, LocalDateTime lastUpdated, FileLocation location) {
        this.estateId = estateId;
        this.filePath = filePath;
        this.lastUpdated = lastUpdated;
        this.location = location;
    }

    public long getEstateId() {
        return estateId;
    }

    public String getFilePath() {
        return filePath;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public FileLocation getLocation() {
        return location;
    }
}
