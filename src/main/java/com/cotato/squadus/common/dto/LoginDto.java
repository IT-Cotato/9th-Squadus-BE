package com.cotato.squadus.common.dto;

import com.cotato.squadus.domain.auth.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginDto {
    private String memberId;
    private String name;
    private String role;

    public LoginDto(Member member){
        this.memberId = member.getMemberId();
        this.name = member.getUsername();
        this.role = member.getRoleKey();
    }

    @Builder
    public LoginDto(String memberId, String name, String role){
        this.memberId = getMemberId();
        this.name = getName();
        this.role = role;
    }
}