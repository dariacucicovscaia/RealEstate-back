package com.daria.realestate.service.report;

import org.apache.poi.ss.usermodel.Workbook;

public interface FileOperations {
    String saveFile(String filePath, Workbook workbook);
}
