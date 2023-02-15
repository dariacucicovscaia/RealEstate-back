package com.daria.realestate.domain;

import com.daria.realestate.dto.enums.FileLocation;

import java.time.LocalDateTime;

public class Report {
    private long estateId;
    private String driveFileId;
    private String localFilePath;
    private LocalDateTime lastUpdated;
    private FileLocation location;

    public Report(long estateId, String driveFileId, String localFilePath, LocalDateTime lastUpdated, FileLocation location) {
        this.estateId = estateId;
        this.driveFileId = driveFileId;
        this.localFilePath = localFilePath;
        this.lastUpdated = lastUpdated;
        this.location = location;
    }

    public Report(long estateId, String driveFileId, LocalDateTime lastUpdated, FileLocation location) {
        this.estateId = estateId;
        this.driveFileId = driveFileId;
        this.lastUpdated = lastUpdated;
        this.location = location;
    }


    public long getEstateId() {
        return estateId;
    }

    public void setEstateId(long estateId) {
        this.estateId = estateId;
    }

    public String getDriveFileId() {
        return driveFileId;
    }

    public void setDriveFileId(String driveFileId) {
        this.driveFileId = driveFileId;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public FileLocation getLocation() {
        return location;
    }

    public void setLocation(FileLocation location) {
        this.location = location;
    }
}
