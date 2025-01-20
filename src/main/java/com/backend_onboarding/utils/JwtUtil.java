package com.backend_onboarding.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.backend_onboarding.domain.member.dto.response.NewTokensResponse;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

	private SecretKey secretKey;
	private static final Long ACCESS_TOKEN_EXPIRED_MS = 60 * 60 * 1000L; // 10 시간
	private static final Long REFRESH_TOKEN_EXPIRED_MS = 60 * 60 * 24 * 7 * 1000L; // 7일

	public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	public String getUsername(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("username", String.class);
	}

	public String getRole(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("role", String.class);
	}

	public Boolean isExpired(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getExpiration()
			.before(new Date());
	}

	public String generateAccessToken(String username, String role) {
		return Jwts.builder()
			.claim("username", username)
			.claim("role", role)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_MS))
			.signWith(secretKey)
			.compact();
	}

	public String generateRefreshToken(String username, String role) {
		return Jwts.builder()
			.claim("username", username)
			.claim("role", role)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_MS))
			.signWith(secretKey)
			.compact();
	}

	public NewTokensResponse refreshAccessToken(String refreshToken) {
		if (refreshToken == null || this.isExpired(refreshToken)) {
			throw new IllegalArgumentException("유효하지 않은 RefreshToken입니다.");
		}
		String username = this.getUsername(refreshToken);
		String role = this.getRole(refreshToken);

		return NewTokensResponse.builder()
			.accessToken(this.generateAccessToken(username, role))
			.refreshToken(this.generateRefreshToken(username, role))
			.build();
	}
}