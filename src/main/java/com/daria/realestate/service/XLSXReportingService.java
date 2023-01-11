package com.daria.realestate.service;


public interface XLSXReportingService {
    /**
     * Saves data into a xlsx file
     *
     * @param sheetName
     * @param fileName
     * @param body      - all data we want inserted
     * @return file path as string
     */
    String saveFile(String sheetName, String fileName, Object[][] body);

    Object[][] readFile(String fileName);
}
