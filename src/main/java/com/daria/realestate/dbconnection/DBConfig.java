package com.daria.realestate.dbconnection;

import com.daria.realestate.util.PropertiesReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DBConfig {
    private final Properties properties;

    public DBConfig() {
        this.properties = new PropertiesReader("application.properties").loadProperties();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(properties.getProperty("url"));
        driverManagerDataSource.setUsername(properties.getProperty("username"));
        driverManagerDataSource.setPassword(properties.getProperty("password"));
        return driverManagerDataSource;
    }
}
