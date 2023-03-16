package com.daria.realestate.controller;

import com.daria.realestate.domain.DynamicApplicationConfiguration;
import com.daria.realestate.service.DynamicApplicationConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/config")
@CrossOrigin
public class DynamicApplicationConfigController {
    private final DynamicApplicationConfigService dynamicApplicationConfigService;

    public DynamicApplicationConfigController(DynamicApplicationConfigService dynamicApplicationConfigService) {
        this.dynamicApplicationConfigService = dynamicApplicationConfigService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<DynamicApplicationConfiguration> createDynamicApplicationConfiguration(
            @RequestBody DynamicApplicationConfiguration dynamicApplicationConfiguration) {
        return new ResponseEntity<>(dynamicApplicationConfigService.createDynamicConfig(dynamicApplicationConfiguration), HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/update/{oldConfigurationType}/{newConfigurationType}/{configurationName}")
    public ResponseEntity<DynamicApplicationConfiguration> updateToActive(
            @PathVariable String oldConfigurationType,
            @PathVariable String newConfigurationType,
            @PathVariable String configurationName
    ) {
        return new ResponseEntity<>(dynamicApplicationConfigService.updateConfigurationStatus(oldConfigurationType,newConfigurationType,configurationName), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/{configurationName}/{configurationStatus}")
    public ResponseEntity<DynamicApplicationConfiguration> getDynamicApplicationConfiguration(@PathVariable String configurationName, @PathVariable boolean configurationStatus) {
        return new ResponseEntity<>(dynamicApplicationConfigService.getDynamicConfigByConfigNameAndStatus(configurationName, configurationStatus), HttpStatus.OK);
    }


}
