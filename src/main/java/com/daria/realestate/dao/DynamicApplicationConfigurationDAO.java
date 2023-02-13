package com.daria.realestate.dao;

import com.daria.realestate.domain.DynamicApplicationConfiguration;

public interface DynamicApplicationConfigurationDAO {
    DynamicApplicationConfiguration create(DynamicApplicationConfiguration dynamicApplicationConfiguration);
    DynamicApplicationConfiguration updateValue(DynamicApplicationConfiguration dynamicApplicationConfiguration);
    DynamicApplicationConfiguration getByConfigNameAndStatus(String configurationName, String configStatus);
    DynamicApplicationConfiguration getByConfigType(String configurationType);

}
