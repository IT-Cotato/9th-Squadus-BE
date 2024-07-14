package com.cotato.squadus.domain.auth.service;


import com.cotato.squadus.api.auth.dto.JoinRequest;
import com.cotato.squadus.domain.auth.entity.Member;
import com.cotato.squadus.domain.auth.enums.MemberRole;
import com.cotato.squadus.domain.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void joinProcess(JoinRequest joinRequest) {

        String username = joinRequest.getUsername();
        String password = joinRequest.getPassword();
        String email = joinRequest.getEmail();

        Boolean isExist = memberRepository.existsByUsername(username);

        if (isExist) {

            return;
        }

        Member member = Member.builder()
                .username(username)
                .memberRole(MemberRole.MEMBER)
                .email(email)
                .build();

        memberRepository.save(member);
    }
}
