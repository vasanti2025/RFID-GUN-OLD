package com.loyalstring.modelclasses;

import com.google.gson.annotations.SerializedName;

public class jjjresponse {

    @SerializedName("ack")
    private int ack;

    @SerializedName("msg")
    private String msg;

    // Getters and setters
    public int getAck() {
        return ack;
    }

    public void setAck(int ack) {
        this.ack = ack;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

