package com.daria.realestate.service;

import com.google.api.services.drive.model.File;

public interface DriveService {
    File getFileByName(String fileName);
    java.io.File download(long estateId, String realFileId);
}
