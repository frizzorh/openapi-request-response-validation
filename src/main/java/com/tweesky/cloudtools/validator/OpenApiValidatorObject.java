package com.tweesky.cloudtools.validator;

import java.util.Map;
import java.util.Set;

public class OpenApiValidatorObject {

    private String method;
    private String path;
    private Set<Map.Entry<String, String>> headers;
    private String requestBody;
    private String responseBody;
    private int status;
    private String responseContentType;

    private OpenApiValidatorObject(String method) {
        this.method = method;
    }

    public static OpenApiValidatorObject forMethod(String method) {
        return new OpenApiValidatorObject(method.toUpperCase());
    }

    public OpenApiValidatorObject withPath(String path) {
        this.path = path;
        return this;
    }

    public OpenApiValidatorObject withHeaders(Set<Map.Entry<String, String>> headers) {
        this.headers = headers;
        return this;
    }

    public OpenApiValidatorObject withRequestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public OpenApiValidatorObject withResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public OpenApiValidatorObject withResponseContentType(String responseContentType) {
        this.responseContentType = responseContentType;
        return this;
    }

    public OpenApiValidatorObject withStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Map.Entry<String, String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Set<Map.Entry<String, String>> headers) {
        this.headers = headers;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponseContentType() {
        return responseContentType;
    }

    public void setResponseContentType(String responseContentType) {
        this.responseContentType = responseContentType;
    }
}
