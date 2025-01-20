package com.backend_onboarding.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend_onboarding.domain.member.dto.request.SignupRequest;
import com.backend_onboarding.domain.member.dto.response.NewTokensResponse;
import com.backend_onboarding.domain.member.dto.response.RefreshAccessTokenResponse;
import com.backend_onboarding.domain.member.dto.response.SignupResponse;
import com.backend_onboarding.domain.member.service.MemberService;
import com.backend_onboarding.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member Controller", description = "회원 관련 API")
public class MemberController {

	private final MemberService memberService;
	private final JwtUtil jwtUtil;

	@Operation(summary = "회원 가입", description = "새로운 회원 등록.")
	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest req) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(memberService.signup(req));
	}

	@Operation(summary = "accesstoken 갱신", description = "refreshtoken을 통해 새로운 accesstoken 재발급.")
	@PostMapping("/refresh")
	public ResponseEntity<RefreshAccessTokenResponse> reGenerateAccessToken(
		@CookieValue(name = "refresh_token", required = false) String refreshToken,
		HttpServletResponse response
	) {
		NewTokensResponse newTokens = memberService.refreshAccessToken(refreshToken);
		// 사용된 refreshToken 재발급. 기존 토큰은...?
		response.addCookie(createCookie("refresh_token", newTokens.getRefreshToken(), 60 * 60 * 24 * 7));
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new RefreshAccessTokenResponse(newTokens.getAccessToken()));
	}

	private Cookie createCookie(String name, String value, int maxAgeSeconds) {
		Cookie refreshTokenCookie = new Cookie(name, value);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setMaxAge(maxAgeSeconds);
		return refreshTokenCookie;
	}
}
