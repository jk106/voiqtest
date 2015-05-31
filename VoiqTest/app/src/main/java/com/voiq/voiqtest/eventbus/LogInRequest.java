package com.voiq.voiqtest.eventbus;

/**
 * Created by juanchaparro on 31/05/15.
 */
public class LogInRequest {

    private String email;
    private String password;

    public LogInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
