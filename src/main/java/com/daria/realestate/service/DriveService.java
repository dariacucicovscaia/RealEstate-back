package com.daria.realestate.service;


import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface DriveService {
    List<File> getAllFilesByName(String fileName);

    String uploadFileIntoDrive(String fileName, java.io.File fileToInsert) throws IOException, GeneralSecurityException;
}
