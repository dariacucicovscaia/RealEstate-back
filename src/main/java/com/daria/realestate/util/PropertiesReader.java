package com.daria.realestate.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//TODO: use Logger
public class PropertiesReader {
    protected Properties loadProperties(String propertiesFilename) {

        Properties prop = new Properties();

        ClassLoader loader = this.getClass().getClassLoader();
        try (InputStream stream = loader.getResourceAsStream(propertiesFilename)) {
            prop.load(stream);
        } catch (IOException e) {
            e.getMessage();
        }

        return prop;
    }
}
