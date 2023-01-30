package com.daria.realestate.service.impl;

import com.daria.realestate.service.XLSXReportingService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class XLSXReportingServiceImpl implements XLSXReportingService {

    private final Workbook workbook;

    public XLSXReportingServiceImpl() {
        this.workbook = new XSSFWorkbook();
    }

    @Override
    public String saveFile(String sheetName, String fileName, Object[][] body) {
        Sheet sheet = workbook.createSheet(sheetName);
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
                    cell.setCellValue(((Enum<?>) value).name());
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                } else if (value instanceof LocalDateTime) {
                    cell.setCellValue(value.toString().replace('T', ' '));
                } else {
                    cell.setCellValue((String) value);
                }
            }
        }

        return insertAllIntoFile(fileName);
    }

    @Override
    public Object[][] readFile(String fileName) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(1).getLastCellNum();
            Object[][] body = new Object[rows][cols];
            for (int r = 0; r < rows; r++) {
                XSSFRow row = sheet.getRow(r);
                for (int c = 0; c < cols; c++) {
                    body[r][c] = row.getCell(c).getStringCellValue();
                }
            }
            return body;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                System.out.println("Failed to close streams");
            }
        }
    }

    private String insertAllIntoFile(String fileName) {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            workbook.write(fos);

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

}
