package com.backend_onboarding.domain.member.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class NewTokensResponse {
	private String accessToken;
	private String refreshToken;

	@Builder
	public NewTokensResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
