package com.daria.realestate.service.impl;

import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.domain.DynamicApplicationConfiguration;
import com.daria.realestate.service.DynamicApplicationConfigService;
import org.springframework.stereotype.Service;

@Service
public class DynamicApplicationConfigServiceImpl implements DynamicApplicationConfigService {
private final DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO;

    public DynamicApplicationConfigServiceImpl(DynamicApplicationConfigurationDAO dynamicApplicationConfigurationDAO) {
        this.dynamicApplicationConfigurationDAO = dynamicApplicationConfigurationDAO;
    }

    @Override
    public DynamicApplicationConfiguration createDynamicConfig(DynamicApplicationConfiguration dynamicApplicationConfiguration) {
        return dynamicApplicationConfigurationDAO.create(dynamicApplicationConfiguration);
    }

    @Override
    public DynamicApplicationConfiguration updateDynamicConfigValue(DynamicApplicationConfiguration dynamicApplicationConfiguration) {
        return dynamicApplicationConfigurationDAO.updateValue(dynamicApplicationConfiguration);
    }

    @Override
    public DynamicApplicationConfiguration getDynamicConfigByConfigName(String configurationName) {
        return dynamicApplicationConfigurationDAO.getByConfigName(configurationName);
    }
}
