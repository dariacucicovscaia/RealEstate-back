package com.daria.realestate.service.report;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

public interface FileOperations {
    String saveFile(String filePath, Workbook workbook);

    File getFile(long estateId, String fileName);
}
