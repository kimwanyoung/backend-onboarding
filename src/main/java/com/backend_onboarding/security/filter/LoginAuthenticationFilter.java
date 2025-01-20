package com.backend_onboarding.security.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend_onboarding.security.CustomUserDetails;
import com.backend_onboarding.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public LoginAuthenticationFilter(String customLoginPath, AuthenticationManager authenticationManager,
		JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.setFilterProcessesUrl(customLoginPath);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) throws IOException {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

		String username = userDetails.getUsername();
		String role = authentication.getAuthorities()
			.stream()
			.findFirst()
			.map(GrantedAuthority::getAuthority)
			.orElseThrow(() -> new IllegalArgumentException("권한이 존재하지 않습니다."));

		String token = jwtUtil.generateAccessToken(username, role);
		String refreshToken = jwtUtil.generateRefreshToken(username, role);

		response.addCookie(createCookie("refresh_token", refreshToken, 60 * 60 * 24 * 7));

		Map<String, String> responseBody = Map.of("accesstoken", token);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getWriter(), responseBody);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		Map<String, String> responseBody = Map.of(
			"message", "로그인에 실패하였습니다."
		);
		new ObjectMapper().writeValue(response.getWriter(), responseBody);
	}

	private Cookie createCookie(String name, String value, int maxAgeSeconds) {
		Cookie refreshTokenCookie = new Cookie(name, value);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setMaxAge(maxAgeSeconds);
		return refreshTokenCookie;
	}
}
