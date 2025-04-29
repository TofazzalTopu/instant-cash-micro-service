package com.info.auth.dto;

import java.io.Serializable;

public class RefreshTokenRequest implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    public String refreshToken;

    public RefreshTokenRequest() {
    }

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
