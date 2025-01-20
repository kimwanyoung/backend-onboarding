package com.backend_onboarding.domain.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend_onboarding.domain.member.dto.request.SignupRequest;
import com.backend_onboarding.domain.member.dto.response.NewTokensResponse;
import com.backend_onboarding.domain.member.dto.response.SignupResponse;
import com.backend_onboarding.domain.member.entity.Member;
import com.backend_onboarding.domain.member.entity.MemberRole;
import com.backend_onboarding.domain.member.repository.MemberRepository;
import com.backend_onboarding.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder encoder;

	public SignupResponse signup(SignupRequest req) {
		if (memberRepository.existsByUsername(req.getUsername())) {
			throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
		}

		Member member = Member.builder()
			.username(req.getUsername())
			.password(encoder.encode(req.getPassword()))
			.nickname(req.getNickname())
			.role(MemberRole.ROLE_USER)
			.build();
		Member savedMember = memberRepository.save(member);
		return SignupResponse.of(savedMember);
	}

	public NewTokensResponse refreshAccessToken(String refreshToken) {
		return jwtUtil.refreshAccessToken(refreshToken);
	}
}
