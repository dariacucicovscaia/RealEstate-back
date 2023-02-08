package com.daria.realestate.service;

import java.time.LocalDateTime;

public interface LocalReportService {
    String generateReport(LocalDateTime from, LocalDateTime to, long estateId, String estateAddress);
}
