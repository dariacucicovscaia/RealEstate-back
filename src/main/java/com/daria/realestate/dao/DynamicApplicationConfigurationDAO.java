package com.daria.realestate.dao;

import com.daria.realestate.domain.DynamicApplicationConfiguration;

public interface DynamicApplicationConfigurationDAO {
    DynamicApplicationConfiguration create(DynamicApplicationConfiguration dynamicApplicationConfiguration);
    DynamicApplicationConfiguration updateValue(DynamicApplicationConfiguration dynamicApplicationConfiguration);
    DynamicApplicationConfiguration getByConfigName(String configurationName);
    DynamicApplicationConfiguration getByConfigType(String configurationType);

}
