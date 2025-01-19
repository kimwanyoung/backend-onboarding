package com.backend_onboarding.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend_onboarding.domain.member.entity.Member;
import com.backend_onboarding.domain.member.entity.MemberRole;
import com.backend_onboarding.security.CustomUserDetails;
import com.backend_onboarding.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authorization.split(" ")[1];
		if (jwtUtil.isExpired(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		String username = jwtUtil.getUsername(token);
		String role = jwtUtil.getRole(token);
		Member member = Member.builder()
			.username(username)
			.role(MemberRole.of(role))
			.build();

		CustomUserDetails customUserDetails = new CustomUserDetails(member);
		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
			customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);
	}
}
