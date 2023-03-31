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
        return dynamicApplicationConfigurationDAO.createDynamicApplicationConfiguration(dynamicApplicationConfiguration);
    }

    @Override
    public DynamicApplicationConfiguration getDynamicConfigByConfigNameAndStatus(String configurationName, boolean configurationStatus) {
        return dynamicApplicationConfigurationDAO.getByConfigNameAndStatus(configurationName, configurationStatus);
    }

    @Override
    public DynamicApplicationConfiguration updateConfigurationStatus(String oldConfigurationType, String newConfigurationType, String configurationName) {
        //1. change the status of the old config to disabled
        dynamicApplicationConfigurationDAO.changePropertyStatusFlag(configurationName, oldConfigurationType);
        //2. change status of new config to active
        return dynamicApplicationConfigurationDAO.changePropertyStatusFlag(configurationName, newConfigurationType);
    }
}
