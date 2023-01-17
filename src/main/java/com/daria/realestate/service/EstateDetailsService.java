package com.daria.realestate.service;

import com.daria.realestate.domain.EstateDetails;

public interface EstateDetailsService {
    EstateDetails getByEstateId(Long estateId);
    EstateDetails createEstateDetails(EstateDetails estateDetails);
}
