package com.daria.realestate.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static final Logger logger = LogManager.getLogger(PropertiesReader.class);
    protected Properties loadProperties(String propertiesFilename) {
        Properties prop = new Properties();

        ClassLoader loader = this.getClass().getClassLoader();
        try (InputStream stream = loader.getResourceAsStream(propertiesFilename)) {
            prop.load(stream);
            logger.info("loaded resources as stream");
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return prop;
    }
}
