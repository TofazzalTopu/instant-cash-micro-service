package com.info.auth.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String accessToken;
	private final String refreshToken;

	public JwtResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getJwttoken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}


}