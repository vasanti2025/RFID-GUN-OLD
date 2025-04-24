package com.loyalstring.LatestApis;

import com.google.gson.annotations.SerializedName;
import com.loyalstring.LatestApis.LoginApiSupport.Employee;

public class LoginResponse {
    @SerializedName("Employee")
    private Employee employee;

    @SerializedName("token")
    private String token;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "employee=" + employee +
                ", token='" + token + '\'' +
                '}';
    }
}