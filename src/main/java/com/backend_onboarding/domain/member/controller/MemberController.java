package com.backend_onboarding.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend_onboarding.domain.member.dto.request.SignupRequest;
import com.backend_onboarding.domain.member.dto.response.SignupResponse;
import com.backend_onboarding.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest req) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(memberService.signup(req));
	}
}
