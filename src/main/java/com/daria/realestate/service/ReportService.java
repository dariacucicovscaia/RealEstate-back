package com.daria.realestate.service;


import java.io.File;
import java.time.LocalDateTime;

public interface ReportService {

    String generateLocalReport(LocalDateTime from, LocalDateTime to, long estateId, String estateAddress);

    String uploadReportToDrive(long estateId,String fileName, File fileToInsert);

    File downloadReportFromDrive(long estateId, String fileNameFromDrive);

}
