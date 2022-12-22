package com.daria.realestate.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private static Connection con = null;
    private PropertiesReader propertiesReader;

    private static DataBaseConnection instance;

    private DataBaseConnection() {
        this.propertiesReader = new PropertiesReader();
        Properties properties = propertiesReader.loadProperties("database.properties");

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static DataBaseConnection getInstance() {
        if (instance == null) {
            getConnection();
        }
        return instance;
    }

    public static Connection getConnection() {
        if (con == null) {
            instance = new DataBaseConnection();
        }
        return con;
    }
}
