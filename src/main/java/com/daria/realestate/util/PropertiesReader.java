package com.daria.realestate.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private String fileName;

    public PropertiesReader(String fileName) {
        this.fileName = fileName;
    }

    private static final Logger logger = LogManager.getLogger(PropertiesReader.class);
    public Properties loadProperties() {
        Properties prop = new Properties();

        ClassLoader loader = this.getClass().getClassLoader();
        try (InputStream stream = loader.getResourceAsStream(fileName)) {
            prop.load(stream);
            logger.info("loaded resources as stream");
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return prop;
    }

    public String getFileName() {
        return fileName;
    }
}
