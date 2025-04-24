package com.loyalstring.LatestApis;

public class LoginRequest {
    private String Username;
    private String Password;

    public LoginRequest(String username, String password) {
        this.Username = username;
        this.Password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}