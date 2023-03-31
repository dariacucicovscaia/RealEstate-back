package com.daria.realestate.service.report;

import com.daria.realestate.configuration.DriveConfig;
import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.domain.Report;
import com.daria.realestate.service.DriveService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
@Qualifier("driveServiceImpl")
@Scope("prototype")
public class DriveReportServiceImpl implements FileOperations, DriveService {
    private static final String APPLICATION_NAME = "RealEstate";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private Drive DRIVE;
    private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;
    private final ReportDAO reportDAO;
    private final String MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public DriveReportServiceImpl(DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO, ReportDAO reportDAO) {
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
        this.reportDAO = reportDAO;
    }

    public Drive getDriveService() {
        if (DRIVE != null) {
            return DRIVE;
        }
        final NetHttpTransport HTTP_TRANSPORT;
        Credential credential;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            credential = getCredentials(HTTP_TRANSPORT);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        DRIVE = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
        return DRIVE;
    }

    public Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException, GeneralSecurityException {
        InputStream in = DriveConfig.class.getResourceAsStream(dynamicApplicationConfigurationDAO.getByConfigType("drive").getBody());

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();


        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("orderprocessoradm1n@gmail.com");

        //    saveProperty("OauthAccessToken", credential.getAccessToken());
        return credential;
    }


    @Override
    public File getFileByName(String fileName) {
        FileList result;
        try {
            result = getDriveService().files().list()
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
                String fileId = getDriveService().files().get(getFileByName(fileName).getId()).execute().getId();
                File file = new File();
                file.setName(filePath);

                FileContent mediaContent = new FileContent(MIME_TYPE, new java.io.File(path));

                uploadedFile = getDriveService().files().update(fileId, file, mediaContent).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File file = new File();
            file.setName(fileName);

            FileContent fileContent = new FileContent(MIME_TYPE, new java.io.File(path));

            try {
                uploadedFile = getDriveService().files().create(file, fileContent).setFields("id").execute();

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
            getDriveService().files().get(realFileId)
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
