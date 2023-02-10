package com.daria.realestate.service.factory;

import com.daria.realestate.configuration.DriveConfig;
import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.domain.Report;
import com.daria.realestate.domain.enums.FileLocation;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class DriveReportServiceImpl implements FileOperations {
    private final DriveConfig driveConfig;
    private final ReportDAO reportDAO;
    private final String MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public DriveReportServiceImpl(DriveConfig driveConfig, ReportDAO reportDAO) {
        this.driveConfig = driveConfig;
        this.reportDAO = reportDAO;
    }

    //    @Override
    public File getFileByName(String fileName) {
        FileList result;
        try {
            result = driveConfig.getDriveService().files().list()
                    .setQ("mimeType='" + MIME_TYPE + "'")
                    .setQ("name = '" + fileName + "'")
                    .setSpaces("drive")
                    .setFields("files(id, name, mimeType)")
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (result.getFiles().size() != 0) {
            return result.getFiles().get(0);
        } else return null;
    }

    @Override
    public String saveFile(LocalDateTime from, LocalDateTime to, long estateId) {
        String filePath = reportDAO.getById(estateId).getLocalFilePath();
        int lastIndex = filePath.lastIndexOf("/");
        String fileName = filePath.substring(lastIndex + 1);
        File uploadedFile;
        if (getFileByName(fileName) != null) {
            try {
                String fileId = driveConfig.getDriveService().files().get(getFileByName(fileName).getId()).execute().getId();
                File file = new File();
                file.setName(filePath);

                FileContent mediaContent = new FileContent(MIME_TYPE, new java.io.File(filePath));

                uploadedFile = driveConfig.getDriveService().files().update(fileId, file, mediaContent).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File file = new File();
            file.setName(fileName);

            FileContent fileContent = new FileContent(MIME_TYPE, new java.io.File(filePath));

            try {
                uploadedFile = driveConfig.getDriveService().files().create(file, fileContent).setFields("id").execute();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        reportDAO.update(new Report(estateId, uploadedFile.getId(), filePath, LocalDateTime.now(), FileLocation.DRIVE));

        return uploadedFile.getId();
    }

    public ByteArrayOutputStream downloadFile(String fileName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            driveConfig.getDriveService().files().get(getFileByName(fileName).getId())
                    .executeMediaAndDownloadTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream;
    }

    @Override
    public java.io.File getFile(long estateId, String fileName) {
        Report report = reportDAO.getById(estateId);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(report.getLocalFilePath());
            downloadFile(fileName).writeTo(fileOutputStream);

            reportDAO.update(new Report(estateId, getFileByName(fileName).getId(), report.getLocalFilePath(), LocalDateTime.now(), FileLocation.DRIVE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return new java.io.File(report.getLocalFilePath());
    }
}
