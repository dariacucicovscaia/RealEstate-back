package com.daria.realestate.service.impl;

import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.dao.impl.ReportDAOImpl;
import com.daria.realestate.domain.Report;
import com.daria.realestate.domain.enums.FileLocation;
import com.daria.realestate.service.DriveService;
import com.daria.realestate.service.PrepExcelObjectService;
import com.daria.realestate.service.ReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReportServiceImpl implements ReportService {

    private final Workbook workbook;
    private final ReportDAO reportDAO;
    private final DriveService driveService;
    private final PrepExcelObjectService prepExcelObjectService;
    @Autowired
    private Environment env;

    public ReportServiceImpl(ReportDAOImpl reportDAO, DriveServiceImpl driveService, PrepExcelObjectServiceImpl prepExcelObjectService) {
        this.reportDAO = reportDAO;
        this.driveService = driveService;
        this.prepExcelObjectService = prepExcelObjectService;
        this.workbook = new XSSFWorkbook();
    }


    @Override
    public String generateLocalReport(LocalDateTime from, LocalDateTime to, long estateId, String estateAddress) {
        Object[][] body = prepExcelObjectService.prepareReportDataForEstateAppointments(from, to, estateId);

        String filename = env.getProperty("rootReportPath") +"/"+ estateAddress + "/report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd"))+".xlsx";
        return insertBodyInFile(estateId, filename, body);
    }

    @Override
    public String uploadReportToDrive(long estateId,String fileName, File fileToInsert) {
        String driveFileId = driveService.uploadFileIntoDrive(fileName, fileToInsert);
        reportDAO.update(new Report(estateId, driveFileId,  LocalDateTime.now(), FileLocation.DRIVE));
        return driveFileId;
    }

    @Override
    public File downloadReportFromDrive(long estateId, String fileNameFromDrive) {
        Report report = reportDAO.getById(estateId);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(report.getLocalFilePath());
            driveService.downloadFile(fileNameFromDrive).writeTo(fileOutputStream);

            reportDAO.update(new Report(estateId, driveService.getFileByName(fileNameFromDrive).getId(), report.getLocalFilePath(), LocalDateTime.now(), FileLocation.DRIVE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new File(report.getLocalFilePath());
    }


    private String createLocalFile(String fileName, long estateId) {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            workbook.write(fos);

            reportDAO.create(new Report(estateId, null, fileName, LocalDateTime.now(), FileLocation.LOCAL));

            return file.getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                System.out.println("Failed to close streams");
            }
        }
    }

    private String insertBodyInFile(Long estateId, String fileName, Object[][] body) {
        Sheet sheet = workbook.createSheet(String.valueOf(estateId));
        int rows = body.length;
        int columns = body[0].length;

        for (int r = 0; r < rows; r++) {
            Row row = sheet.createRow(r);

            for (int c = 0; c < columns; c++) {
                Cell cell = row.createCell(c);
                sheet.setColumnWidth(c, 25 * 256);

                Object value = body[r][c];
                if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Long) {
                    cell.setCellValue((Long) value);
                } else if (value instanceof Enum) {
                    cell.setCellValue(((Enum<?>) value).toString());
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                } else if (value instanceof LocalDateTime) {
                    cell.setCellValue(value.toString().replace('T', ' '));
                } else {
                    cell.setCellValue((String) value);
                }
            }
        }

        return createLocalFile(fileName, estateId);
    }

}
