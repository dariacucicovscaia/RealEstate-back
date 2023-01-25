package com.daria.realestate.controller;

import com.daria.realestate.domain.Estate;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.service.EstateRegistrationService;
import com.daria.realestate.service.EstateService;
import com.daria.realestate.service.impl.EstateRegistrationServiceImpl;
import com.daria.realestate.service.impl.EstateServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/estate")
public class EstateController {
    private final EstateService estateService;
    private final EstateRegistrationService estateRegistrationService;

    public EstateController(EstateServiceImpl estateService, EstateRegistrationServiceImpl estateRegistrationService) {
        this.estateService = estateService;
        this.estateRegistrationService = estateRegistrationService;
    }

    @GetMapping("/{estateId}")
    public Estate getEstateById(@PathVariable long estateId){
        return estateService.getEstateById(estateId);
    }

    @PostMapping("/create/{userId}")
    public EstateDTO createEstate(@RequestBody EstateDTO estateDTO, @PathVariable long userId){
        return estateRegistrationService.createEstate(estateDTO, userId);
    }

}
