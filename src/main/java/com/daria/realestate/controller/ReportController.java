package com.daria.realestate.controller;

import com.daria.realestate.service.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/download/{fileNameFromDrive}/{estateId}")
    public ResponseEntity<Resource> download(@PathVariable String fileNameFromDrive, @PathVariable long estateId) throws IOException {
        File file = reportService.downloadReportFromDrive(estateId, fileNameFromDrive);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());

        ByteArrayInputStream resource = new ByteArrayInputStream(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(resource));
    }

    @PostMapping("/generate/{from}/{to}/{estateId}/{estateAddress}")
    public String generateReport(@PathVariable String from, @PathVariable String to, @PathVariable long estateId, @PathVariable String estateAddress) {
        return reportService.generateLocalReport(LocalDateTime.parse(from), LocalDateTime.parse(to), estateId, estateAddress);
    }

    @PostMapping("/saveToDrive/{fileName}/{estateId}")
    public String saveToDrive(@RequestParam("filePath") String filePath, @PathVariable String fileName,@PathVariable long estateId) {
        return reportService.uploadReportToDrive(estateId, fileName, new java.io.File(filePath));
    }
}