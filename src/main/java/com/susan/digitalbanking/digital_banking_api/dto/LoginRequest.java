package com.susan.digitalbanking.digital_banking_api.dto;

public class LoginRequest {

    private String password;
    private String username;

    public LoginRequest(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
