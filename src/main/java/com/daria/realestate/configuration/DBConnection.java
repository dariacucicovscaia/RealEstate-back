package com.daria.realestate.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnection {
    private static final Logger logger = LogManager.getLogger(DBConnection.class);
    private static Connection con = null;
    private PropertiesReader propertiesReader;

    private static DBConnection instance;

    private DBConnection() {
        this.propertiesReader = new PropertiesReader("application.properties");
        Properties properties = propertiesReader.loadProperties();

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        try {
            con = DriverManager.getConnection(url, username, password);
            logger.info("connection created credentials: url - "
                    + url + " username - " + username
            );
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }

    }


    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        if (con == null) {
            getConnection();
        }
        return con;
    }
}
