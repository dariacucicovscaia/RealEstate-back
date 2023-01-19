package com.daria.realestate.service;

import com.daria.realestate.domain.User;
import com.daria.realestate.dto.EstateDTO;

public interface EstateRegistrationService {
    EstateDTO createEstate(EstateDTO estateDTO, long ownerId);
}
