package com.daria.realestate.dao;

import com.daria.realestate.domain.DynamicApplicationConfiguration;

public interface DynamicApplicationConfigurationDAO {
    DynamicApplicationConfiguration createDynamicApplicationConfiguration(DynamicApplicationConfiguration dynamicApplicationConfiguration);
    DynamicApplicationConfiguration getByConfigNameAndStatus(String configurationName, boolean configStatus);
    DynamicApplicationConfiguration getByConfigType(String configurationType);
    DynamicApplicationConfiguration changePropertyStatusFlag(String configurationName, String configurationType);
}
