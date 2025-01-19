package com.backend_onboarding.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		// TODO: 권한 설정 추가
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
			null);

		return authenticationManager.authenticate(authToken);
	}
}
