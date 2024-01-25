package com.tweesky.cloudtools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "provider")
public class ProviderConfig {

    private Map<String, URL> schemasLocations;

    public void setSchemasLocations(Map<String, URL> schemasLocations) {
        this.schemasLocations = schemasLocations;
    }

    public Map<String, URL> getSchemasLocations() {
        return schemasLocations;
    }
}
