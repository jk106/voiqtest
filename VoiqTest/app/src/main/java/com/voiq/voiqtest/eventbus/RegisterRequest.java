package com.voiq.voiqtest.eventbus;

/**
 * Created by juanchaparro on 31/05/15.
 */
public class RegisterRequest {

    private String usrPicName;
    private String userId;
    private int campaignId;
    private String undercover;
    private String firstName;
    private String lastName;
    private String email;
    private String birth;
    private String zipCode;
    private String phone;
    private int androidDevice;
    private String password1;
    private String password2;

    public RegisterRequest(String usrPicName, String userId, int campaignId, String undercover,
                           String firstName, String lastName, String email, String birth,
                           String zipCode, String phone, int androidDevice, String password1,
                           String password2) {
        this.usrPicName = usrPicName;
        this.userId = userId;
        this.campaignId = campaignId;
        this.undercover = undercover;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birth = birth;
        this.zipCode = zipCode;
        this.phone = phone;
        this.androidDevice = androidDevice;
        this.password1 = password1;
        this.password2 = password2;
    }

    public String getUsrPicName() {
        return usrPicName;
    }

    public String getUserId() {
        return userId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public String getUndercover() {
        return undercover;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getBirth() {
        return birth;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public int getAndroidDevice() {
        return androidDevice;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }
}
