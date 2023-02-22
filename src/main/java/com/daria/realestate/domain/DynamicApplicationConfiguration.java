package com.daria.realestate.domain;

public class DynamicApplicationConfiguration {
    private String configName;
    private String body;
    private String configType;
    private String status;


    public DynamicApplicationConfiguration(String configName, String configType, String body, String status) {
        this.configName = configName;
        this.body = body;
        this.configType = configType;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }
}
