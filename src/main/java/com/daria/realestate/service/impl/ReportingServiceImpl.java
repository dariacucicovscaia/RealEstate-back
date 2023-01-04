package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.impl.AppointmentDAOImpl;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.service.ReportingService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ReportingServiceImpl implements ReportingService {


    private final AppointmentDAO appointmentDAO;

    public ReportingServiceImpl(AppointmentDAOImpl appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }


    private void createCell(Row row, int columnCount, Object value) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
    }


    public void createAppointmentReport(LocalDateTime from, LocalDateTime to) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd_hh-mm-ss");
        String formattedTime = LocalDateTime.now().format(dateTimeFormatter);
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "report_" + formattedTime + ".xlsx";

        Workbook workbook = new XSSFWorkbook();

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        Sheet sheet = workbook.createSheet(LocalDateTime.now().format(dateTimeFormatter)+"_"+LocalDateTime.now().plusWeeks(1).format(dateTimeFormatter) );


        Row row = sheet.createRow(0);
        Field[] allFields = EstateDTO.class.getDeclaredFields();
        int count = 0;
        for (Field estateDTOField : allFields) {
            createCell(row, count++, estateDTOField.getName());
        }


        FileOutputStream fos = new FileOutputStream(fileLocation);

        workbook.write(fos);
        fos.close();
        System.out.println(fileLocation + " written successfully");
    }
}
