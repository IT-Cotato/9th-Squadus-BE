package com.cotato.squadus.domain.auth.repository;
import com.cotato.squadus.domain.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByUsername(String username);

    Member findByUsername(String username);

    Optional<Member> findByMemberId(String memberId);

}
