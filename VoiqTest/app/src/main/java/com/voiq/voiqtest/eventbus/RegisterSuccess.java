package com.voiq.voiqtest.eventbus;

import com.voiq.voiqtest.api.RegisterResponse;

/**
 * Created by juanchaparro on 31/05/15.
 */
public class RegisterSuccess {

    private RegisterResponse response;

    public RegisterResponse getResponse() {
        return response;
    }

    public void setResponse(RegisterResponse response) {
        this.response = response;
    }
}
