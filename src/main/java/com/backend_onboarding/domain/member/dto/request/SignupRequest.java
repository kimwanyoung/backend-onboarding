package com.backend_onboarding.domain.member.dto.request;

import lombok.Data;

@Data
public class SignupRequest {
	private String username;
	private String password;
	private String nickname;
}
