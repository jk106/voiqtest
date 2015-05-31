package com.voiq.voiqtest.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by juanchaparro on 31/05/15.
 */
public class LogInResponse {

    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    private String orgName;
    private String image;
    private String city;
    private String orgId;
    private String g;
    private String h;
    private String color;
    private String userType;
    private String campaignId;
    private String name;
    private String userId;
    private String campaignType;
    private String loginType;
    private String hasFacebook;
    private String hasLinkedin;
    @SerializedName("class")
    private String classField;
    @SerializedName("zipcode")
    private String zipCode;
    private String idNumber;
    private String bank;
    private String bankAccountNumber;
    private String knowledge;
    private String token;
    private String user_status;
    private String nAssignedLeads;

}
