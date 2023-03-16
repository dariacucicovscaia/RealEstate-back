package com.daria.realestate.controller;

import com.daria.realestate.domain.Estate;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.dto.EstateSearchFilter;
import com.daria.realestate.dto.Page;
import com.daria.realestate.service.EstateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/estate")
@CrossOrigin
public class EstateController {
    private final EstateService estateService;

    public EstateController(EstateService estateService) {
        this.estateService = estateService;
    }



    @GetMapping("/allDetails/{estateId}")
    public EstateDTO getAllEstateDetails(@PathVariable long estateId) {
        return estateService.getAllDetailsOfAnEstate(estateId);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping()
    public EstateDTO createEstate(@RequestBody EstateDTO estateDTO) {
        return estateService.createEstate(estateDTO);
    }


    @PostMapping("/estatesByAllCriteria")
    public Page<Estate> getAllEstatesByAllCriteria(@RequestBody EstateSearchFilter estateSearchFilter,
                                                   @RequestParam("page") int page, @RequestParam("size") int size) {
        return estateService.getEstatesFilteredByAllEstateCriteria(estateSearchFilter, size, page);
    }
    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/ownersEstates/{ownerId}")
    public Page<Estate> getOwnersEstates(@PathVariable long ownerId,  @RequestParam("page") int page, @RequestParam("size") int size) {
        return estateService.getOwnerEstates(ownerId, page, size);
    }
}