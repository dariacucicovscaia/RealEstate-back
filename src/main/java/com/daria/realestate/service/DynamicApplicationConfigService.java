package com.daria.realestate.service;

import com.daria.realestate.domain.DynamicApplicationConfiguration;

public interface DynamicApplicationConfigService {
    DynamicApplicationConfiguration createDynamicConfig(DynamicApplicationConfiguration dynamicApplicationConfiguration);
    DynamicApplicationConfiguration updateDynamicConfigValue(DynamicApplicationConfiguration dynamicApplicationConfiguration);
    DynamicApplicationConfiguration getDynamicConfigByConfigNameAndStatus(String configurationName, String configurationStatus);
}
