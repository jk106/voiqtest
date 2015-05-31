package com.voiq.voiqtest.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by juanchaparro on 30/05/15.
 */
public interface VoiqEndpoints {


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

    @FormUrlEncoded
    @POST("/dt_users.php?f=login")
    void LogIn(@Field("email") String email,
               @Field("password") String password,
               Callback<LogInResponse> cb);
}
