package com.daria.realestate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;
//TODO refactor using Environment Variables
@Configuration
public class DataSourceConfig {
    private final Properties properties;

    public DataSourceConfig() {
        this.properties = new PropertiesReader("application.properties").loadProperties();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(properties.getProperty("spring.datasource.url"));
        driverManagerDataSource.setUsername(properties.getProperty("spring.datasource.username"));
        driverManagerDataSource.setPassword(properties.getProperty("spring.datasource.password"));
        return driverManagerDataSource;
    }
}
