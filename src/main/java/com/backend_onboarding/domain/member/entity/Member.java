package com.backend_onboarding.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBERS")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String nickname;
	@Column(nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	private MemberRole role;

	@Builder
	public Member(Long id, String username, String nickname, String password, MemberRole role) {
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
	}
}
