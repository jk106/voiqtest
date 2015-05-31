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
    private String status;
    private String text;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getHasFacebook() {
        return hasFacebook;
    }

    public void setHasFacebook(String hasFacebook) {
        this.hasFacebook = hasFacebook;
    }

    public String getHasLinkedin() {
        return hasLinkedin;
    }

    public void setHasLinkedin(String hasLinkedin) {
        this.hasLinkedin = hasLinkedin;
    }

    public String getClassField() {
        return classField;
    }

    public void setClassField(String classField) {
        this.classField = classField;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getnAssignedLeads() {
        return nAssignedLeads;
    }

    public void setnAssignedLeads(String nAssignedLeads) {
        this.nAssignedLeads = nAssignedLeads;
    }

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
