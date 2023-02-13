package com.daria.realestate.controller;

import com.daria.realestate.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/report")
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

//    @GetMapping("/get/{simpleName}/{estateId}/{location}")
//    public ResponseEntity<Resource> getFile(@PathVariable String simpleName, @PathVariable long estateId, @PathVariable FileLocation location) throws IOException {
//        File file = reportService.getReport(estateId, simpleName, location);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
//
//        ByteArrayInputStream resource = new ByteArrayInputStream(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(file.length())
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(new InputStreamResource(resource));

//    }

}