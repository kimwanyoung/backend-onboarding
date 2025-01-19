package com.backend_onboarding.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.backend_onboarding.domain.member.dto.request.SignupRequest;
import com.backend_onboarding.domain.member.dto.response.SignupResponse;
import com.backend_onboarding.domain.member.entity.Member;
import com.backend_onboarding.domain.member.entity.MemberRole;
import com.backend_onboarding.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	MemberRepository memberRepository;
	@Mock
	BCryptPasswordEncoder encoder;
	@InjectMocks
	MemberService memberService;

	@Test
	void 정상_회원가입_테스트() {
		//given
		SignupRequest req = SignupRequest.builder()
			.nickname("username")
			.password("password")
			.username("username")
			.build();

		when(memberRepository.existsByUsername(req.getUsername())).thenReturn(false);
		when(memberRepository.save(any())).thenReturn(Member.builder()
			.nickname("username")
			.username("username")
			.role(MemberRole.ROLE_USER)
			.build());

		//when
		SignupResponse response = memberService.signup(req);

		//then
		assertThat(response.getNickname()).isEqualTo(req.getNickname());
		assertThat(response.getUsername()).isEqualTo(req.getUsername());
		assertThat(response.getAuthorities().size()).isGreaterThan(0);
	}

	@Test
	void 중복_회원_예외_테스트() {
		//given
		SignupRequest req = SignupRequest.builder()
			.nickname("username")
			.password("password")
			.username("username")
			.build();

		when(memberRepository.existsByUsername(req.getUsername())).thenReturn(true);

		//when && then
		assertThatThrownBy(() -> memberService.signup(req))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("이미 존재하는");
	}
}