package com.voiq.voiqtest.eventbus;

import com.voiq.voiqtest.api.LogInResponse;

/**
 * Created by juanchaparro on 31/05/15.
 * Event which notifies the successful (internet-wise, 2xx code) response on the log in request
 */
public class LogInSuccess {

    /**
     * Log in response
     */
    private LogInResponse response;

    /**
     * Getter for the response attribute
     * @return the log in response
     */
    public LogInResponse getResponse() {
        return response;
    }

    /**
     * Setter for the response property
     * @param response the response to be set
     */
    public void setResponse(LogInResponse response) {
        this.response = response;
    }
}
