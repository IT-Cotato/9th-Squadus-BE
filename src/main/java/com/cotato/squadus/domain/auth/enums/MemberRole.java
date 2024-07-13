package com.cotato.squadus.domain.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    MEMBER("ROLE_MEMBER", "일반 회원"),
    CERTIFIED_MEMBER("ROLE_CERTIFIED_MEMBER", "학교 인증 회원");

    private final String key;
    private final String title;
}
