package org.example.thejavatest.member;

import org.example.thejavatest.domain.Member;
import org.example.thejavatest.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
