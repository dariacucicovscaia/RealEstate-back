package com.daria.realestate.service.impl;

import com.daria.realestate.dao.AppointmentDAO;
import com.daria.realestate.dao.impl.AppointmentDAOImpl;
import com.daria.realestate.domain.User;
import com.daria.realestate.dto.AppointmentDTO;
import com.daria.realestate.service.ReportingService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReportingServiceImpl implements ReportingService {
    private final AppointmentDAO appointmentDAO;
    private Workbook workbook;

    public ReportingServiceImpl(AppointmentDAOImpl appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
        this.workbook = new XSSFWorkbook();
    }

    private String getFileName(String user) {
        return "C:\\Users\\DCUCICOV\\Documents\\reports\\" + user + "\\report_" + LocalDateTime.now().toLocalDate() + ".xlsx";
    }

    private boolean saveFile(String user) throws IOException {
        File file = new File(getFileName(user));
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            workbook.write(fos);

            return true;
        } finally {
            try {
                if (fos != null) {
                    fos.close();

                }
            } catch (IOException e) {
                //TODO cleanup exceptions and log them
                System.out.println("Failed to close streams");
            }
        }
    }

    private void createCell(Row row, int columnCount, Object value) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue(value.toString().replace('T', ' '));
        } else {
            cell.setCellValue((String) value);
        }
    }

    public List<AppointmentDTO> readDataOfAUserFromExcelFile(String user) throws IOException {
        List<AppointmentDTO> listAppointments;
        FileInputStream inputStream = null;
        try {

            inputStream = new FileInputStream(getFileName(user));
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            listAppointments = new ArrayList<>();
            for (int r = 1; r <= rows; r++) {
                XSSFRow row = sheet.getRow(r);

                listAppointments.add(new AppointmentDTO(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(), LocalDateTime.parse(row.getCell(4).getStringCellValue().replace(' ', 'T')), LocalDateTime.parse(row.getCell(5).getStringCellValue().replace(' ', 'T')), (long) (row.getCell(6).getNumericCellValue())));
            }

        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                System.out.println("Failed to close streams");
            }
        }

        return listAppointments;
    }

    private void setHeaderValues(Sheet sheet) {
        int columnCount = 0;
        Row row = sheet.createRow(0);

        createCell(row, columnCount++, "Owner's email");
        createCell(row, columnCount++, "Owner's first name");
        createCell(row, columnCount++, "Owner's last name");
        createCell(row, columnCount++, "Owner's phone number");
        createCell(row, columnCount++, "Start of appointment");
        createCell(row, columnCount++, "End of appointment");
        createCell(row, columnCount++, "Estate id");

        for (int i = 0; i < columnCount; i++)
            sheet.setColumnWidth(i, 25 * 256);
    }

    public Boolean createAppointmentReportWithTimeInterval(LocalDateTime from, LocalDateTime to, List<User> users) {
        boolean successful = false;
        Sheet sheet = workbook.createSheet(from.toLocalDate() + "_" + to.toLocalDate());
        setHeaderValues(sheet);

        for (User user : users) {
            List<AppointmentDTO> appointments = appointmentDAO.getAppointmentsWithASpecificTimeInterval(from, to, user.getEmail());

            int rowCount = 1;
            Row row;

            for (AppointmentDTO appointmentDTO : appointments) {
                row = sheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, appointmentDTO.getEmail());
                createCell(row, columnCount++, appointmentDTO.getFirstName());
                createCell(row, columnCount++, appointmentDTO.getLastName());
                createCell(row, columnCount++, appointmentDTO.getPhoneNumber());
                createCell(row, columnCount++, appointmentDTO.getStart());
                createCell(row, columnCount++, appointmentDTO.getEnd());
                createCell(row, columnCount++, appointmentDTO.getEstateId());

            }
            try {
                successful = saveFile(user.getEmail());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return successful;

    }


}
