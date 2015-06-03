package com.voiq.voiqtest.api;

/**
 * Created by juanchaparro on 31/05/15.
 * Response for the register request, to be serialized using Gson
 */
public class RegisterResponse {

    /**
     * The parameters for the request, getters and setters useful for Gson and for
     * the app.
     */

    private String status;

    private String text;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
