package com.daria.realestate.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface DriveService {

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException;


    List<File> getAllFilesFromDrive() throws IOException, GeneralSecurityException;

    String uploadFileIntoDrive(String fileName, java.io.File fileToInsert) throws IOException, GeneralSecurityException;
}
