package com.daria.realestate.controller;

import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.Page;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.dto.EstateSearchFilter;
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

    public EstateController(EstateService estateService, EstateRegistrationService estateRegistrationService) {
        this.estateService = estateService;
        this.estateRegistrationService = estateRegistrationService;
    }

    @GetMapping("/{estateId}")
    public Estate getEstateById(@PathVariable long estateId) {
        return estateService.getEstateById(estateId);
    }

    @GetMapping("/allDetails/{estateId}")
    public EstateDTO getAllEstateDetails(@PathVariable long estateId) {
        return estateService.getAllDetailsOfAnEstate(estateId);
    }

    @PostMapping("/create/{userId}")
    public EstateDTO createEstate(@RequestBody EstateDTO estateDTO, @PathVariable long userId) {
        return estateRegistrationService.createEstate(estateDTO, userId);
    }

    @GetMapping("/estates/{paymentTransactionType}/{acquisitionStatus}/pag")
    public Page<Estate> getAllEstatesByCriteriaPaginated(@RequestParam("page") int page, @RequestParam("size") int size, @PathVariable PaymentTransactionType paymentTransactionType, @PathVariable AcquisitionStatus acquisitionStatus) {
        return estateService.getAllEstatesFilteredByPaymentTransactionTypeAndAcquisitionStatus(size, page, paymentTransactionType, acquisitionStatus);
    }

    @GetMapping("/estatesByAllCriteria")
    public Page<Estate> getAllEstatesByAllCriteria(@RequestBody EstateSearchFilter estateSearchFilter, @RequestParam("page") int page, @RequestParam("size") int size){
        return estateService.getEstatesFilteredByAllEstateCriteria(estateSearchFilter, size, page);
    }
}
