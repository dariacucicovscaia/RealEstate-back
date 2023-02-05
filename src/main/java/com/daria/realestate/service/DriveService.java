package com.daria.realestate.service;


import com.google.api.services.drive.model.File;

import java.io.ByteArrayOutputStream;

public interface DriveService {
    File getFileByName(String fileName);
    ByteArrayOutputStream downloadFile(String fileName);
    String uploadFileIntoDrive(String fileName, java.io.File fileToInsert);
}
