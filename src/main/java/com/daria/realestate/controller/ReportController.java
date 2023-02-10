package com.daria.realestate.controller;

import com.daria.realestate.domain.enums.FileLocation;
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

    @GetMapping("/get/{simpleName}/{estateId}/{location}")
    public ResponseEntity<Resource> getFile(@PathVariable String simpleName, @PathVariable long estateId, @PathVariable FileLocation location) throws IOException {
        File file = reportService.getReport(estateId, simpleName, location);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());

        ByteArrayInputStream resource = new ByteArrayInputStream(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(resource));
    }

    @PostMapping("/generate/{from}/{to}/{estateId}/{location}")
    public String generateLocalReport(@PathVariable String from, @PathVariable String to, @PathVariable long estateId, @PathVariable FileLocation location) {
        return reportService.generateReport(LocalDateTime.parse(from), LocalDateTime.parse(to), estateId, location);
    }

}