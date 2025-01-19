package com.backend_onboarding.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend_onboarding.domain.member.dto.request.SignupRequest;
import com.backend_onboarding.domain.member.dto.response.RefreshAccessTokenResponse;
import com.backend_onboarding.domain.member.dto.response.SignupResponse;
import com.backend_onboarding.domain.member.service.MemberService;
import com.backend_onboarding.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final JwtUtil jwtUtil;

	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest req) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(memberService.signup(req));
	}

	@PostMapping("/refresh")
	public ResponseEntity<RefreshAccessTokenResponse> reGenerateAccessToken(
		@CookieValue(name = "refresh_token", required = false) String refreshToken) {
		if (refreshToken == null || jwtUtil.isExpired(refreshToken)) {
			throw new IllegalArgumentException("유효하지 않은 RefreshToken입니다.");
		}

		String username = jwtUtil.getUsername(refreshToken);
		String role = jwtUtil.getRole(refreshToken);

		String generatedAccessToken = jwtUtil.createJwt(username, role, 60 * 60 * 10L);
		return ResponseEntity.ok(new RefreshAccessTokenResponse(generatedAccessToken));
	}
}
