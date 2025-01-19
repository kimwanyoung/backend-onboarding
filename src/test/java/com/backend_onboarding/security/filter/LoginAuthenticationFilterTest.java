package com.backend_onboarding.security.filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.backend_onboarding.domain.member.entity.Member;
import com.backend_onboarding.domain.member.entity.MemberRole;
import com.backend_onboarding.domain.member.repository.MemberRepository;

@SpringBootTest
@AutoConfigureMockMvc
class LoginAuthenticationFilterTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	@Transactional
	void 로그인_정상_테스트() throws Exception {
		// given
		String username = "username";
		String password = "password";
		String nickname = "username";
		memberRepository.save(
			Member.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.nickname(nickname)
				.role(MemberRole.ROLE_USER)
				.build()
		);

		// when
		ResultActions result = mockMvc.perform(post("/sign")
			.param("username", username)
			.param("password", password));

		// then
		result.andExpect(status().isOk())
			.andExpect(jsonPath("$.accesstoken").isNotEmpty());
	}

	@Test
	@Transactional
	void 로그인_실패_예외_테스트() throws Exception {
		// given
		String username = "username";
		String password = "password";
		String nickname = "nickname";
		memberRepository.save(
			Member.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.nickname(nickname)
				.role(MemberRole.ROLE_USER)
				.build()
		);

		// when
		ResultActions result = mockMvc.perform(post("/sign")
			.param("username", username)
			.param("password", "wrongPassword"));

		// then
		result.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.message").value("로그인에 실패하였습니다."));
	}
}
