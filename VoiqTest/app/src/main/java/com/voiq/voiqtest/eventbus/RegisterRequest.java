package com.voiq.voiqtest.eventbus;

/**
 * Created by juanchaparro on 31/05/15.
 * Event for making a register request
 */
public class RegisterRequest {

    /**
     * usrPicName parameter for register request
     */
    private String usrPicName;

    /**
     * userId parameter for register request
     */
    private String userId;

    /**
     * campaignId parameter for register request
     */
    private int campaignId;

    /**
     * undercover parameter for register request
     */
    private String undercover;

    /**
     * firstName parameter for register request
     */
    private String firstName;

    /**
     * lastName parameter for register request
     */
    private String lastName;

    /**
     * email parameter for register request
     */
    private String email;

    /**
     * birth parameter for register request
     */
    private String birth;

    /**
     * zipCode parameter for register request
     */
    private String zipCode;

    /**
     * phone parameter for register request
     */
    private String phone;

    /**
     * androidDevice parameter for register request
     */
    private int androidDevice;

    /**
     * password1 parameter for register request
     */
    private String password1;

    /**
     * password2 parameter for register request
     */
    private String password2;

    /**
     * Constructor for the event. Notice that the entire attributes derive their name from the
     * request, using camel-case. We'll take care of the actual names in the retrofit interface
     * @param usrPicName parameter for request
     * @param userId parameter for request
     * @param campaignId parameter for request
     * @param undercover parameter for request
     * @param firstName parameter for request
     * @param lastName parameter for request
     * @param email parameter for request
     * @param birth parameter for request
     * @param zipCode parameter for request
     * @param phone parameter for request
     * @param androidDevice parameter for request
     * @param password1 parameter for request
     * @param password2 parameter for request
     */
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

    /**
     * Getter for usrPicName attribute
     * @return usrPicName
     */
    public String getUsrPicName() {
        return usrPicName;
    }

    /**
     * Getter for userId attribute
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Getter for campaignId attribute
     * @return campaingId
     */
    public int getCampaignId() {
        return campaignId;
    }

    /**
     * Getter for undercover attribute
     * @return undercover
     */
    public String getUndercover() {
        return undercover;
    }

    /**
     * Getter for firstName attribute
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for lastName attribute
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for email attribute
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for birth attribute
     * @return birth
     */
    public String getBirth() {
        return birth;
    }

    /**
     * Getter for zipCode attribute
     * @return zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Getter for phone attribute
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Getter for androidDevice attribute
     * @return androidDevice
     */
    public int getAndroidDevice() {
        return androidDevice;
    }

    /**
     * Getter for password1 attribute
     * @return password1
     */
    public String getPassword1() {
        return password1;
    }

    /**
     * Getter for password2 attribute
     * @return password2
     */
    public String getPassword2() {
        return password2;
    }
}
