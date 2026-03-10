package com.susan.digitalbanking.digital_banking_api.dto;

public class RefreshRequest {

    public String refreshToken;

    public RefreshRequest(){}

    public String getRefreshToken(){
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

}
