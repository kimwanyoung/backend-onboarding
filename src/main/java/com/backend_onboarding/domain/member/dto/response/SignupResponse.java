package com.backend_onboarding.domain.member.dto.response;

import java.util.List;

import com.backend_onboarding.domain.member.entity.MemberRole;

import lombok.Builder;
import lombok.Data;

@Data
public class SignupResponse {
	private String username;
	private String nickname;
	private List<RoleResponse> authorities;

	@Data
	public static class RoleResponse {
		private MemberRole authorityName;

		@Builder
		public RoleResponse(MemberRole authorityName) {
			this.authorityName = authorityName;
		}
	}

	@Builder
	public SignupResponse(String username, String nickname, List<RoleResponse> authorities) {
		this.username = username;
		this.nickname = nickname;
		this.authorities = authorities;
	}
}
