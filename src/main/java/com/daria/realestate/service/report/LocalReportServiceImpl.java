package com.daria.realestate.service.report;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Qualifier("localReportServiceImpl")
@Scope("prototype")
public class LocalReportServiceImpl implements FileOperations {

    @Override
    public String saveFile(String filePath, Workbook workbook) {
        File file = new File(filePath);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
            //  reportDAO.create(new Report(estateId, null, filePath, LocalDateTime.now(), FileLocation.local));
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
