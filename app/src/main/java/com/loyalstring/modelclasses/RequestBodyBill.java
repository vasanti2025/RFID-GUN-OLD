package com.loyalstring.modelclasses;

public class RequestBodyBill {
    private String voucher_id;
    private String rfid_value;
    private String username;
    private String password;
    private String user_id;

    public RequestBodyBill(String voucher_id, String rfid_value, String username, String password, String user_id) {
        this.voucher_id = voucher_id;
        this.rfid_value = rfid_value;
        this.username = username;
        this.password = password;
        this.user_id = user_id;
    }

    public RequestBodyBill() {
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getRfid_value() {
        return rfid_value;
    }

    public void setRfid_value(String rfid_value) {
        this.rfid_value = rfid_value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
