package org.acme.openapi.validationproxy.schema;

import org.acme.openapi.validationproxy.validator.OpenApiValidator;

public interface OpenApiSchemaValidatorProvider {

    OpenApiValidator get(String openApiDefinitionKey);

}
