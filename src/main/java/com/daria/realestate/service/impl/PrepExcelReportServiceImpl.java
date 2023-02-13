package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.dao.EstateDAO;
import com.daria.realestate.dto.AppointmentReportDTO;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.service.PrepExcelReportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PrepExcelReportServiceImpl implements PrepExcelReportService {
    private final Workbook workbook;
    private final EstateDAO estateDAO;
    private final AppointmentDAO appointmentDAO;
    private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;

    public PrepExcelReportServiceImpl(EstateDAO estateDAO, AppointmentDAO appointmentDAO, DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO) {
        this.estateDAO = estateDAO;
        this.appointmentDAO = appointmentDAO;
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
        this.workbook = new XSSFWorkbook();
    }

    @Override
    public Object[][] prepareReportDataForEstateAppointments(LocalDateTime from, LocalDateTime to, long estateId) {
        String[] headers = {"Start", "End", "Email", "FirstName", "LastName", "PhoneNumber", "AppointmentStatus"};
        List<AppointmentReportDTO> appointments = appointmentDAO.getAppointmentsWithASpecificTimeIntervalByEstateId(from, to, estateId);
        Object[][] result = setHeader(headers, appointments.size());

        int rows = appointments.size();

        for (AppointmentReportDTO appointmentReportDTO : appointments) {
            for (int i = 1; i <= rows; i++) {
                result[i][0] = appointmentReportDTO.getStart();
                result[i][1] = appointmentReportDTO.getEnd();
                result[i][2] = appointmentReportDTO.getEmail();
                result[i][3] = appointmentReportDTO.getFirstName();
                result[i][4] = appointmentReportDTO.getLastName();
                result[i][5] = appointmentReportDTO.getPhoneNumber();
                result[i][6] = appointmentReportDTO.getAppointmentStatus();
            }
        }

        return result;
    }

    private Workbook insertBodyInFile(Long estateId, Object[][] body, LocalDateTime from, LocalDateTime to) {
        Sheet sheet = workbook.createSheet("report-" + from.format(DateTimeFormatter.ofPattern("yyyy-mm-dd")) + "--" + to.format(DateTimeFormatter.ofPattern("yyyy-mm-dd")) + "-estate-" + estateId);
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
                    cell.setCellValue((value).toString());
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                } else if (value instanceof LocalDateTime) {
                    cell.setCellValue(value.toString().replace('T', ' '));
                } else {
                    cell.setCellValue((String) value);
                }
            }
        }

        return workbook;
    }

    private Object[][] setHeader(String[] values, int arrL) {
        Object[][] headers = new Object[arrL + 1][values.length];

        for (int i = 0; i < values.length; i++) {
            headers[0][i] = values[i];
        }

        return headers;
    }


    @Override
    public Workbook generateReport(LocalDateTime from, LocalDateTime to, long estateId) {
        Object[][] body = prepareReportDataForEstateAppointments(from, to, estateId);

        return insertBodyInFile(estateId, body, from, to);
    }

    @Override
    public String generateLocalFileName(long estateId) {
        EstateDTO estate = estateDAO.getAllEstateDetails(estateId);

        return dynamicApplicationConfigurationDAO.getByConfigType("local").getBody() + "/" + estate.getFullAddress().replace(" ", "_") + "/report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")) + ".xlsx";
    }


}
