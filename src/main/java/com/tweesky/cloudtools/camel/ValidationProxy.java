package com.tweesky.cloudtools.camel;

import com.tweesky.cloudtools.schema.OpenApiSchemaValidatorProvider;
import com.tweesky.cloudtools.validator.OpenApiValidator;
import com.tweesky.cloudtools.validator.OpenApiValidatorObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.tweesky.cloudtools.utils.HttpUtils.filterCamelHeaders;

@Component
public class ValidationProxy extends RouteBuilder {

    public static final String REQUEST_VALIDATION_ERRORS = "OpenApiValidator_requestValidationErrors";
    @Autowired
    OpenApiSchemaValidatorProvider validatorProvider;

    public ValidationProxy() {
        log.info("Test");
    }

    @Override
    public void configure() throws Exception {
        from("netty-http:proxy://0.0.0.0:{{server.port}}")
                .convertBodyTo(String.class)
                .process(exchange -> {
                    log.trace("Validate Request");

                    Message message = exchange.getMessage();
                    String openApiSchemaKey = message.getHeader("OpenApiSchemaKey", String.class);
                    if (openApiSchemaKey != null) {
                        OpenApiValidator validator = validatorProvider.get(openApiSchemaKey);
                        var requestValidation = validator.validate(
                                OpenApiValidatorObject
                                        .forMethod(message.getHeader(Exchange.HTTP_METHOD).toString())
                                        .withPath(message.getHeader(Exchange.HTTP_PATH).toString())
                                        .withHeaders(filterCamelHeaders(message.getHeaders()).entrySet())
                                        .withRequestBody(message.getBody(String.class))
                                //.withResponseBody(request.getResponseAsJson())
                                //.withResponseContentType("application/json")
                                //.withStatus(request.getStatusCode())
                        );
                        if (!requestValidation.isValid()) {
                            message.setHeader(REQUEST_VALIDATION_ERRORS, requestValidation.getErrors());
                        }
                    }
                })
                .choice()
                    .when(header(REQUEST_VALIDATION_ERRORS).isNotNull())
                        .to("direct:validationErrors")
                .otherwise()
                .toD("netty-http:"
                        + "${headers." + Exchange.HTTP_SCHEME + "}://"
                        + "${headers." + Exchange.HTTP_HOST + "}:"
                        + "${headers." + Exchange.HTTP_PORT + "}"
                        + "${headers." + Exchange.HTTP_PATH + "}")
                .process(exchange -> {
                    log.trace("Validate response");
                    //todo validate response
                }).end();

        from("direct:validationErrors")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST::code)
                .setBody(header(REQUEST_VALIDATION_ERRORS))
                .marshal().json();
    }
}
