package com.daria.realestate.service;


import com.daria.realestate.domain.enums.FileLocation;

import java.io.File;
import java.time.LocalDateTime;

public interface ReportService {

    String generateReport(LocalDateTime from, LocalDateTime to, long estateId);

    File getReport(long estateId, String simpleName, FileLocation location);

}
