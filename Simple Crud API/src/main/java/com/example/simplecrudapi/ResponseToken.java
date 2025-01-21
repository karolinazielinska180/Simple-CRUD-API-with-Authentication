package com.example.simplecrudapi;

import java.util.Date;

public class ResponseToken {
    private String token;

    private Date expirationDate;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
