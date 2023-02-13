package com.daria.realestate.service.factory;

import com.daria.realestate.dao.ReportDAO;
import com.daria.realestate.domain.Report;
import com.daria.realestate.domain.enums.FileLocation;
import com.daria.realestate.service.PrepExcelReportService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class LocalReportServiceImpl implements FileOperations {
    private final ReportDAO reportDAO;
    private final PrepExcelReportService prepExcelReportService;

    public LocalReportServiceImpl(ReportDAO reportDAO, PrepExcelReportService prepExcelReportService) {
        this.reportDAO = reportDAO;
        this.prepExcelReportService = prepExcelReportService;
    }

    @Override
    public String saveFile(LocalDateTime from, LocalDateTime to, long estateId) {
        String filePath = prepExcelReportService.generateLocalFileName(estateId);
        File file = new File(filePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            prepExcelReportService.generateReport(from, to, estateId).write(fos);
            reportDAO.create(new Report(estateId, null, filePath, LocalDateTime.now(), FileLocation.local));
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

    @Override
    public File getFile(long estateId, String fileName) {
        return null;
    }

}
