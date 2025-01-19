package com.backend_onboarding.domain.member.entity;

import java.util.Arrays;

public enum MemberRole {
	ROLE_USER, ROLE_ADMIN;

	public static MemberRole of(String role) {
		return Arrays.stream(MemberRole.values())
			.filter(memberRole -> memberRole.name().equals(role))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 권한입니다."));
	}
}
