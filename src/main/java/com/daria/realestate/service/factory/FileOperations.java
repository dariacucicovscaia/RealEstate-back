package com.daria.realestate.service.factory;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.time.LocalDateTime;

public interface FileOperations {
    String saveFile(LocalDateTime from, LocalDateTime to, long estateId);

    File getFile(long estateId, String fileName);
}
