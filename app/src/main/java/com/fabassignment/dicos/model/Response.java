package com.fabassignment.dicos.model;

import com.fabassignment.dicos.model.http.RequestTypes;

import java.util.List;
import java.util.Map;

/**
 * Created by benifabrice on 5/12/17.
 */

public class Response {
    private int statusCode;
    private String responseBody;
    private String errorMessage;
    private String optionalMessage;
    private @RequestTypes.Interface int requestType;
    private Map<String, List<String>> headers;

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getRequestType() {
        return requestType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setOptionalMessage(String optionalMessage) {
        this.optionalMessage = optionalMessage;
    }


    public String getOptionalMessage() {
        return optionalMessage;
    }
}
