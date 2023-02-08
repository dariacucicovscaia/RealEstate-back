package com.daria.realestate.service.impl;

import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.domain.Report;
import com.daria.realestate.domain.enums.FileLocation;
import com.daria.realestate.service.LocalReportService;
import com.daria.realestate.service.PrepExcelObjectService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LocalReportServiceImpl implements LocalReportService {
    private final Workbook workbook;
    private final PrepExcelObjectService prepExcelObjectService;
    private final ReportDAO reportDAO;
    private Environment env;

    public LocalReportServiceImpl(PrepExcelObjectService prepExcelObjectService, ReportDAO reportDAO) {
        this.prepExcelObjectService = prepExcelObjectService;
        this.reportDAO = reportDAO;
        this.workbook = new XSSFWorkbook();
    }

    @Autowired
    public void setEnvironment(Environment env) {
        this.env = env;
    }

    @Override
    public String generateReport(LocalDateTime from, LocalDateTime to, long estateId, String estateAddress) {
        Object[][] body = prepExcelObjectService.prepareReportDataForEstateAppointments(from, to, estateId);

        String filename = env.getProperty("rootReportPath") + "/" + estateAddress + "/report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")) + ".xlsx";
        return insertBodyInFile(estateId, filename, body);
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
