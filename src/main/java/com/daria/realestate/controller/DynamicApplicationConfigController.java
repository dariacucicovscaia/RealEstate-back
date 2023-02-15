package com.daria.realestate.controller;

import com.daria.realestate.domain.DynamicApplicationConfiguration;
import com.daria.realestate.service.DynamicApplicationConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/config")
public class DynamicApplicationConfigController {
    private final DynamicApplicationConfigService dynamicApplicationConfigService;

    public DynamicApplicationConfigController(DynamicApplicationConfigService dynamicApplicationConfigService) {
        this.dynamicApplicationConfigService = dynamicApplicationConfigService;
    }

    @PostMapping("/")
    public ResponseEntity<DynamicApplicationConfiguration> createDynamicApplicationConfiguration(
            @RequestBody DynamicApplicationConfiguration dynamicApplicationConfiguration) {
        return new ResponseEntity<>(dynamicApplicationConfigService.createDynamicConfig(dynamicApplicationConfiguration), HttpStatus.CREATED);
    }
    @PutMapping("/update/{oldConfigType}/{oldConfigStatus}")
    public ResponseEntity<DynamicApplicationConfiguration> updateDynamicApplicationConfiguration(
            @RequestBody DynamicApplicationConfiguration dynamicApplicationConfiguration,
            @PathVariable String oldConfigType,
            @PathVariable String oldConfigStatus
    ){
        return new ResponseEntity<>(dynamicApplicationConfigService.updateDynamicConfigValue(dynamicApplicationConfiguration, oldConfigType, oldConfigStatus), HttpStatus.OK);
    }
    @GetMapping("/{configurationName}/{configurationStatus}")
    public ResponseEntity<DynamicApplicationConfiguration> getDynamicApplicationConfiguration(@PathVariable String configurationName, @PathVariable String configurationStatus){
        return new ResponseEntity<>(dynamicApplicationConfigService.getDynamicConfigByConfigNameAndStatus(configurationName, configurationStatus), HttpStatus.OK);
    }


}
