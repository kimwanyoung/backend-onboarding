package com.backend_onboarding.domain.member.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class SignupRequest {
	private String username;
	private String password;
	private String nickname;

	@Builder
	public SignupRequest(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}
}
