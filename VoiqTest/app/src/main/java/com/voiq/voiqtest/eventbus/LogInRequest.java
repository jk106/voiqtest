package com.voiq.voiqtest.eventbus;

/**
 * Created by juanchaparro on 31/05/15.
 * Event for making a log in request to the API.
 */
public class LogInRequest {

    /**
     * E-mail for the log in request
     */
    private String email;

    /**
     * Password for the log in request
     */
    private String password;

    /**
     * Constructor
     * @param email the e-mail for the request
     * @param password the password for the request
     */
    public LogInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Getter for the e-mail attribute
     * @return the e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for the password attribute
     * @return the password
     */
    public String getPassword() {
        return password;
    }

}
