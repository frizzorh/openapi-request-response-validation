package org.acme.openapi.validationproxy.schema;

import org.acme.openapi.validationproxy.config.ProviderConfig;
import org.acme.openapi.validationproxy.validator.OpenApiValidator;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class OpenApiSchemaValidatorProviderImpl implements OpenApiSchemaValidatorProvider {

    private final Logger log = LoggerFactory.getLogger(OpenApiSchemaValidatorProviderImpl.class);

    @Autowired
    ProviderConfig providerConfig;
    private final Map<String, OpenApiValidator> validatorMap = new HashMap<>();
    @Override
    public OpenApiValidator get(String openApiDefinitionKey) {
        if (validatorMap.containsKey(openApiDefinitionKey)) {
            return validatorMap.get(openApiDefinitionKey);
        }
        throw new IllegalArgumentException("No validator configured for openApiDefinitionKey: " + openApiDefinitionKey);
    }

    @PostConstruct
    public void init() {
        for (Map.Entry<String, URL> stringURIEntry : providerConfig.getSchemasLocations().entrySet()) {
            String key = stringURIEntry.getKey();
            URL url = stringURIEntry.getValue();
            log.info("Initializing validator for {} Loading from {}", key, url);
            validatorMap.put(key, new OpenApiValidator(url));
        }
    }

}
