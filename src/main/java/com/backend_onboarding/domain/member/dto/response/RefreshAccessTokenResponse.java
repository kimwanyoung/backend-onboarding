package com.backend_onboarding.domain.member.dto.response;

import lombok.Data;

@Data
public class RefreshAccessTokenResponse {
	private String accessToken;

	public RefreshAccessTokenResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}
