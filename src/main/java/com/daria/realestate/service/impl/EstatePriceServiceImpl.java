package com.daria.realestate.service.impl;

import com.daria.realestate.dao.EstatePriceDAO;
import com.daria.realestate.dao.impl.EstatePriceDAOImpl;
import com.daria.realestate.domain.EstatePrice;
import com.daria.realestate.service.EstatePriceService;
import org.springframework.stereotype.Service;

@Service
public class EstatePriceServiceImpl implements EstatePriceService {
    private final EstatePriceDAO priceDAO;

    public EstatePriceServiceImpl(EstatePriceDAOImpl priceDAO) {
        this.priceDAO = priceDAO;
    }

    @Override
    public EstatePrice createEstatePrice(EstatePrice estatePrice){
        return priceDAO.create(estatePrice);
    }
}
