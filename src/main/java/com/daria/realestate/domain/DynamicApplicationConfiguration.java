package com.daria.realestate.domain;

public class DynamicApplicationConfiguration {
    private String configName;
    private String body;
    private String configType;
    private boolean isActive;


    public DynamicApplicationConfiguration(String configName, String configType, String body, boolean isActive) {
        this.configName = configName;
        this.body = body;
        this.configType = configType;
        this.isActive = isActive;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }
}
