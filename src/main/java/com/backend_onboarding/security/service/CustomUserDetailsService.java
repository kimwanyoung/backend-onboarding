package com.backend_onboarding.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend_onboarding.domain.member.entity.Member;
import com.backend_onboarding.domain.member.repository.MemberRepository;
import com.backend_onboarding.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));
		return new CustomUserDetails(member);
	}
}
