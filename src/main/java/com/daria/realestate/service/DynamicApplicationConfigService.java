package com.daria.realestate.service;

import com.daria.realestate.domain.DynamicApplicationConfiguration;

public interface DynamicApplicationConfigService {
    DynamicApplicationConfiguration createDynamicConfig(DynamicApplicationConfiguration dynamicApplicationConfiguration);
    DynamicApplicationConfiguration getDynamicConfigByConfigNameAndStatus(String configurationName, boolean configurationStatus);

    DynamicApplicationConfiguration updateConfigurationStatus(String oldConfigurationType, String newConfigurationType, String configurationName);
}
