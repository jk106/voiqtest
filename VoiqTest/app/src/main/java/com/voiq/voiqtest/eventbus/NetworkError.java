package com.voiq.voiqtest.eventbus;

import retrofit.RetrofitError;

/**
 * Created by juanchaparro on 31/05/15.
 */
public class NetworkError {

    private RetrofitError error;

    public RetrofitError getError() {
        return error;
    }

    public void setError(RetrofitError error) {
        this.error = error;
    }
}
