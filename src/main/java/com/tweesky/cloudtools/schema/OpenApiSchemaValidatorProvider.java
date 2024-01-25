package com.tweesky.cloudtools.schema;

import com.tweesky.cloudtools.validator.OpenApiValidator;

public interface OpenApiSchemaValidatorProvider {

    OpenApiValidator get(String openApiDefinitionKey);

}
