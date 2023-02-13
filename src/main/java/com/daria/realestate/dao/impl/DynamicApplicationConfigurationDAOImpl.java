package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.DynamicApplicationConfigurationDAO;
import com.daria.realestate.dao.mappers.DynamicApplicationConfigurationMapper;
import com.daria.realestate.domain.DynamicApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DynamicApplicationConfigurationDAOImpl implements DynamicApplicationConfigurationDAO {

    private final JdbcTemplate jdbcTemplate;

    public DynamicApplicationConfigurationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DynamicApplicationConfiguration create(DynamicApplicationConfiguration dynamicApplicationConfiguration) {
        String SQL_CREATE_DYNAMIC_APP_CONFIG = " insert into dynamic_application_configuration (configuration_name, configuration_type, configuration_body, configuration_status ) values ( ? , ? , ? ,?) ";
        jdbcTemplate.update(SQL_CREATE_DYNAMIC_APP_CONFIG,
                dynamicApplicationConfiguration.getConfigName(),
                dynamicApplicationConfiguration.getConfigType(),
                dynamicApplicationConfiguration.getBody(),
                dynamicApplicationConfiguration.getStatus());
        return getByConfigType(dynamicApplicationConfiguration.getConfigType());
    }

    @Override
    public DynamicApplicationConfiguration updateValue(DynamicApplicationConfiguration dynamicApplicationConfiguration) {
        String SQL_UPDATE_VALUE = " update dynamic_application_configuration set configuration_type = ? , configuration_body = ? , configuration_status = ? where configuration_name = ? ";
        jdbcTemplate.update(SQL_UPDATE_VALUE,
                dynamicApplicationConfiguration.getConfigType(),
                dynamicApplicationConfiguration.getBody(),
                dynamicApplicationConfiguration.getStatus(),
                dynamicApplicationConfiguration.getConfigName()
                );
        return getByConfigType(dynamicApplicationConfiguration.getConfigType());
    }

    @Override
    public DynamicApplicationConfiguration getByConfigNameAndStatus(String configurationName, String configStatus) {
        String SQL_GET_VALUE_BY_KEY = " select * from dynamic_application_configuration where configuration_name = ? and configuration_status = ?  ";
        return jdbcTemplate.queryForObject(SQL_GET_VALUE_BY_KEY, new DynamicApplicationConfigurationMapper(),  configurationName, configStatus);
    }

    @Override
    public DynamicApplicationConfiguration getByConfigType(String configurationType) {
        String SQL_GET_VALUE_BY_KEY = " select * from dynamic_application_configuration where configuration_type = ? ";
        return jdbcTemplate.queryForObject(SQL_GET_VALUE_BY_KEY, new DynamicApplicationConfigurationMapper(),  configurationType);
    }
}
