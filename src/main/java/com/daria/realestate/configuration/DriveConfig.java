package com.daria.realestate.configuration;

import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
public class DriveConfig {
    private static final String APPLICATION_NAME = "RealEstate";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;
    private Drive DRIVE;

    public DriveConfig(DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO) {
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
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

}
