package com.backend_onboarding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend_onboarding.security.filter.LoginAuthenticationFilter;
import com.backend_onboarding.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtil;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable) //csrf disable
			.formLogin(AbstractHttpConfigurer::disable) //From 로그인 방식 disable
			.httpBasic(AbstractHttpConfigurer::disable) //http basic 인증 방식 disable
			.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/sign", "/signup").permitAll() // 회원가입, 로그인 경로 인증 제외
				.anyRequest().authenticated())
			.addFilterAt(new LoginAuthenticationFilter(
					"/sign",
					authenticationManager(authenticationConfiguration),
					jwtUtil
				),
				UsernamePasswordAuthenticationFilter.class)
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // jwt 사용을 위해 세션 stateless 상태 설정

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
