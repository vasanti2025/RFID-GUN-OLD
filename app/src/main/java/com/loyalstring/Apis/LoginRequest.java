package com.loyalstring.Apis;

public class LoginRequest {
    private String DeviceSerialNo;
    private String MobileNo;

    public LoginRequest(String deviceSerialNo, String mobileNo) {
        DeviceSerialNo = deviceSerialNo;
        MobileNo = mobileNo;
    }

    // Add getters and setters
}