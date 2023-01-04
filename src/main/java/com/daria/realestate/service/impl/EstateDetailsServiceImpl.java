package com.daria.realestate.service.impl;

import com.daria.realestate.dao.EstateDetailsDAO;
import com.daria.realestate.dao.impl.EstateDetailsDAOImpl;
import com.daria.realestate.domain.EstateDetails;
import com.daria.realestate.service.EstateDetailsService;

public class EstateDetailsServiceImpl implements EstateDetailsService {
    private final EstateDetailsDAO estateDetailsDAO;

    public EstateDetailsServiceImpl(EstateDetailsDAOImpl estateDetailsDAO) {
        this.estateDetailsDAO = estateDetailsDAO;
    }

    @Override
    public EstateDetails getByEstateId(Long estateId) {
        return estateDetailsDAO.getById(estateId);
    }
}
