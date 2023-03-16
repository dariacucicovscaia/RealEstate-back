package com.daria.realestate.controller;

import com.daria.realestate.domain.Report;
import com.daria.realestate.dto.Page;
import com.daria.realestate.service.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/report")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/generate/{from}/{to}/{estateId}")
    public String generateLocalReport(@PathVariable String from,
                                      @PathVariable String to,
                                      @PathVariable long estateId) {

        return reportService.generateReport(LocalDateTime.parse(from), LocalDateTime.parse(to), estateId);
    }

    @GetMapping("/get/{estateId}")
    public ResponseEntity<Resource> getFile(@PathVariable long estateId) throws IOException {
        File file = reportService.getReport(estateId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());

        ByteArrayInputStream resource = new ByteArrayInputStream(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(resource));

    }
    @GetMapping("/reports/{userId}")
    public Page<Report> getAllUsersReports(@PathVariable long userId,@RequestParam("pageSize") int pageSize,@RequestParam("pageNumber") int pageNumber) {
        return reportService.getAllReportsOfAUser(userId, pageSize, pageNumber);
    }
    @GetMapping("/hello")
    public String helloWorld() {
        return " hi from server";
    }

}