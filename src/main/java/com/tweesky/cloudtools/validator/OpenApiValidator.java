package com.tweesky.cloudtools.validator;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.model.Request;
import com.atlassian.oai.validator.model.SimpleRequest;
import com.atlassian.oai.validator.model.SimpleResponse;

import com.atlassian.oai.validator.report.ValidationReport;
import com.tweesky.cloudtools.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

public class OpenApiValidator {

    private final Logger log = LoggerFactory.getLogger(OpenApiValidator.class);

    private OpenApiInteractionValidator validator;

    public OpenApiValidator(String schema) {

        if(schema == null) {
            throw new RuntimeException("OpenAPI schema is undefined");
        }
        this.validator = OpenApiInteractionValidator.createForInlineApiSpecification(schema)
                .build();
    }
    public OpenApiValidator(URL url) {

        if(url == null) {
            throw new RuntimeException("OpenAPI schema URL is undefined");
        }
        this.validator = OpenApiInteractionValidator.createForSpecificationUrl(url.toString())
                .build();
    }

    public ResponseData validate(OpenApiValidatorObject validatorObject) {

        SimpleRequest.Builder requestBuilder = new SimpleRequest.Builder
                (Request.Method.valueOf(validatorObject.getMethod()), validatorObject.getPath());
        if(validatorObject.getRequestBody() != null) {
            requestBuilder.withBody(validatorObject.getRequestBody());
        }
        if(validatorObject.getHeaders() != null) {
            for(var header : validatorObject.getHeaders()) {
                String key = header.getKey();
                String value = header.getValue();
                requestBuilder.withHeader(key, value);
            }
        }

        SimpleResponse.Builder responseBuilder = SimpleResponse.Builder.status(validatorObject.getStatus());
        if(validatorObject.getResponseBody() != null) {
            responseBuilder.withBody(validatorObject.getResponseBody());
        }
        if(validatorObject.getResponseContentType() != null) {
            responseBuilder.withContentType(validatorObject.getResponseContentType());
        }

        ValidationReport validationReport;
        if (validatorObject.getStatus() != 0) {
            validationReport = validator.validate(requestBuilder.build(), responseBuilder.build());
        } else {
            validationReport = validator.validateRequest(requestBuilder.build());
        }

        var responseData = new ResponseData();

        responseData.setValid(!validationReport.hasErrors());

        responseData.setErrors(validationReport.getMessages().stream()
                        .filter(message -> message.getLevel().equals(ValidationReport.Level.ERROR))
                .map(message -> message.getMessage())
                .collect(Collectors.toList()));

        responseData.setOthers(validationReport.getMessages().stream()
                .filter(message -> !message.getLevel().equals(ValidationReport.Level.ERROR))
                .map(message -> message.getMessage())
                .collect(Collectors.toList()));

        if(responseData.isValid()) {
            log.info("#### " + validatorObject.getMethod() + " " + validatorObject.getPath() + " is valid");
        } else {
            log.warn("#### " + validatorObject.getMethod() + " " + validatorObject.getPath() + " has errors");
            responseData.getErrors().stream().forEach(log::warn);
        }

        return responseData;
    }

}
