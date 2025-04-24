package com.loyalstring.modelclasses;

import java.util.ArrayList;

public class jjjcustomermodel {
    public int ack;
    public String msg;
    public ArrayList<UserDatum> user_data;

    public jjjcustomermodel(int ack, String msg, ArrayList<UserDatum> user_data) {
        this.ack = ack;
        this.msg = msg;
        this.user_data = user_data;
    }

    public jjjcustomermodel() {
    }

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

    public ArrayList<UserDatum> getUser_data() {
        return user_data;
    }

    public void setUser_data(ArrayList<UserDatum> user_data) {
        this.user_data = user_data;
    }

    public static class UserDatum{
        public String user_id;
        public String full_name;

        public UserDatum(String user_id, String full_name) {
            this.user_id = user_id;
            this.full_name = full_name;
        }

        public UserDatum() {
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }
    }
}
