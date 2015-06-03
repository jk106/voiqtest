package com.voiq.voiqtest.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by juanchaparro on 30/05/15.
 * API endpoints for app requests. Powered by Retrofit
 */
public interface VoiqEndpoints {


    /**
     * Create User request, with its entire parameter set, annotated for naming purposes of the
     * backend. Annotations also allow to url-encode the request parameters.
     * @param usrPicName
     * @param userId
     * @param campaignId
     * @param undercover
     * @param firstName
     * @param lastName
     * @param email
     * @param birth
     * @param zipCode
     * @param phone
     * @param androidDevice
     * @param password1
     * @param password2
     * @param cb Callback for asynchronous operation
     */
    @FormUrlEncoded
    @POST("/dt_users.php?f=createUser")
    void CreateUser(@Field("usrPicName") String usrPicName,
                    @Field("userId") String userId,
                    @Field("campaignId") int campaignId,
                    @Field("undercover") String undercover,
                    @Field("firstName") String firstName,
                    @Field("lastName") String lastName,
                    @Field("email") String email,
                    @Field("birth") String birth,
                    @Field("zipcode") String zipCode,
                    @Field("phone1") String phone,
                    @Field("androidDevice") int androidDevice,
                    @Field("password1") String password1,
                    @Field("password2") String password2,
                    Callback<RegisterResponse> cb);

    /**
     * Log In request, fields annotated for url encoding purposes
     * @param email
     * @param password
     * @param cb Callback for asynchronous operation.
     */
    @FormUrlEncoded
    @POST("/dt_users.php?f=login")
    void LogIn(@Field("email") String email,
               @Field("password") String password,
               Callback<LogInResponse> cb);
}
