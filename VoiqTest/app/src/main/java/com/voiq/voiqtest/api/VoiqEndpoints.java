package com.voiq.voiqtest.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by juanchaparro on 30/05/15.
 */
public interface VoiqEndpoints {

    @POST("/dt_users.php?f=createUser")
    void CreateUser(@Body String body,
               Callback<String> cb);

    @POST("/dt_users.php?f=login")
    void LogIn(@Body String body,
               Callback<String> cb);
}
