package com.voiq.voiqtest.eventbus;

import com.voiq.voiqtest.api.RegisterResponse;

/**
 * Created by juanchaparro on 31/05/15.
 * Event which notifies the successful register request (Code 2xx)
 */
public class RegisterSuccess {

    /**
     * Request response
     */
    private RegisterResponse response;

    /**
     * Getter for the response attribute
     * @return the response
     */
    public RegisterResponse getResponse() {
        return response;
    }

    /**
     * Setter for the response attribute
     * @param response to be set for the event
     */
    public void setResponse(RegisterResponse response) {
        this.response = response;
    }
}
