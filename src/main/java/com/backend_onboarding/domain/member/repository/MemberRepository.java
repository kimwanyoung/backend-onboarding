package com.backend_onboarding.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend_onboarding.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
