package com.backend_onboarding.domain.member.service;

import org.springframework.stereotype.Service;

import com.backend_onboarding.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
}
