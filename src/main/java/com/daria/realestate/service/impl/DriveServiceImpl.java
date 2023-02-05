package com.daria.realestate.service.impl;

import com.daria.realestate.configuration.PropertiesReader;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class DriveServiceImpl implements DriveService {
    private static final String APPLICATION_NAME = "RealEstate";
    private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private final String MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final Logger logger;

    private final NetHttpTransport HTTP_TRANSPORT;
    private final Drive DRIVE;


    public DriveServiceImpl() {
        try {
            this.HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            this.DRIVE = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
            logger = LoggerFactory.getLogger(DriveServiceImpl.class);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveProperty(String key, String value) throws IOException {
        PropertiesReader propsReader = new PropertiesReader("application.properties");

        Properties properties = propsReader.loadProperties();

        properties.setProperty(key, value);
        properties.store(new FileOutputStream("C:\\Users\\DCUCICOV\\RealEstate\\src\\main\\resources\\application.properties"), "");
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = DriveServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }


        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();


        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("orderprocessoradm1n@gmail.com");

        saveProperty("OauthAccessToken", credential.getAccessToken());
        return credential;
    }

    @Override
    public File getFileByName(String fileName) {
        FileList result;
        try {
            result = DRIVE.files().list()
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
    public String uploadFileIntoDrive(String fileName, java.io.File fileToInsert) {
        File uploadedFile;
        if (getFileByName(fileName) != null) {
            try {
                String fileId = DRIVE.files().get(getFileByName(fileName).getId()).execute().getId();
                File file = new File();
                file.setName(fileName);

                FileContent mediaContent = new FileContent(MIME_TYPE, fileToInsert);

                uploadedFile = DRIVE.files().update(fileId, file, mediaContent).execute();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File file = new File();
            file.setName(fileName);

            FileContent fileContent = new FileContent(MIME_TYPE, fileToInsert);

            try {
                uploadedFile = DRIVE.files().create(file, fileContent).setFields("id").execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return  uploadedFile.getId();

    }

    @Override
    public ByteArrayOutputStream downloadFile(String fileName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            DRIVE.files().get(getFileByName(fileName).getId())
                    .executeMediaAndDownloadTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream;
    }
}
