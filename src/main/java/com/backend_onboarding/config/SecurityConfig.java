package com.backend_onboarding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable) //csrf disable
			.formLogin(AbstractHttpConfigurer::disable) //From 로그인 방식 disable
			.httpBasic(AbstractHttpConfigurer::disable) //http basic 인증 방식 disable
			.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/sign", "/signup").permitAll() // 회원가입, 로그인 경로 인증 제외
				.anyRequest().authenticated())
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // jwt 사용을 위해 세션 stateless 상태 설정

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
