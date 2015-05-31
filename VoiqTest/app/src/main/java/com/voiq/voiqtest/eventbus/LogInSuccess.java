package com.voiq.voiqtest.eventbus;

import com.voiq.voiqtest.api.LogInResponse;

/**
 * Created by juanchaparro on 31/05/15.
 */
public class LogInSuccess {

    private LogInResponse response;

    public LogInResponse getResponse() {
        return response;
    }

    public void setResponse(LogInResponse response) {
        this.response = response;
    }
}
