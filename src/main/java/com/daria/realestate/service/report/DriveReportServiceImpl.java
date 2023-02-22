package com.daria.realestate.service.report;

import com.daria.realestate.configuration.DriveConfig;
import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.domain.Report;
import com.daria.realestate.service.DriveService;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Qualifier("driveServiceImpl")
@Scope("prototype")
public class DriveReportServiceImpl implements FileOperations, DriveService {
    private final DriveConfig driveConfig;
    private final ReportDAO reportDAO;
    private final String MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public DriveReportServiceImpl(DriveConfig driveConfig, ReportDAO reportDAO) {
        this.driveConfig = driveConfig;
        this.reportDAO = reportDAO;
    }

    @Override
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
    public String saveFile(String filePath, Workbook workbook) {
        String path = saveFileOnLocalMachine(filePath, workbook);
        int lastIndex = path.lastIndexOf("\\");
        String fileName = path.substring(lastIndex + 1);
        File uploadedFile;
        if (getFileByName(fileName) != null) {
            try {
                String fileId = driveConfig.getDriveService().files().get(getFileByName(fileName).getId()).execute().getId();
                File file = new File();
                file.setName(filePath);

                FileContent mediaContent = new FileContent(MIME_TYPE, new java.io.File(path));

                uploadedFile = driveConfig.getDriveService().files().update(fileId, file, mediaContent).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File file = new File();
            file.setName(fileName);

            FileContent fileContent = new FileContent(MIME_TYPE, new java.io.File(path));

            try {
                uploadedFile = driveConfig.getDriveService().files().create(file, fileContent).setFields("id").execute();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return uploadedFile.getId();
    }

    private String saveFileOnLocalMachine(String filePath, Workbook workbook) {
        java.io.File file = new java.io.File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);
            workbook.close();

            return file.getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ByteArrayOutputStream downloadFile(String realFileId) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            driveConfig.getDriveService().files().get(realFileId)
                    .executeMediaAndDownloadTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream;
    }

    @Override
    public java.io.File download(long estateId, String realFileId) {
        Report report = reportDAO.getById(estateId);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(report.getFilePath());
            downloadFile(realFileId).writeTo(fileOutputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return new java.io.File(report.getFilePath());
    }
}
