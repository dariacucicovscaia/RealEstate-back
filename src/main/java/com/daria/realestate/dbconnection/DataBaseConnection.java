package com.daria.realestate.dbconnection;

import com.daria.realestate.util.PropertiesReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private static final Logger logger = LogManager.getLogger(DataBaseConnection.class);
    private static Connection con = null;
    private PropertiesReader propertiesReader;

    private static DataBaseConnection instance;

    private DataBaseConnection() {
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

    public static DataBaseConnection getInstance() {
        if (instance == null) {
            instance = new DataBaseConnection();
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
