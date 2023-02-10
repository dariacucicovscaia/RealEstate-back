package com.daria.realestate.domain;

public class DynamicApplicationConfiguration {
    /**
     * UNIQUE - acts like a primary key
     * rootPath - local
     * credentialsPath - drive
     * API_KEY - mail
     */
    private String configPath;
    /**
     * local -
     * drive - applicationName
     * mail - mail.smtp.host
     */
    private String configurationName;
    /**
     * local/drive/mail
     */
    private String configType;


    public DynamicApplicationConfiguration(String configurationName, String configPath, String configType) {
        this.configPath = configPath;
        this.configurationName = configurationName;
        this.configType = configType;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }
}
