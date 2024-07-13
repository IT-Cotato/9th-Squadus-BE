package com.cotato.squadus.common.dto;

import com.cotato.squadus.domain.auth.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginDto {
    private String uniqueId;
    private String username;
    private String role;

    public LoginDto(Member member){
        this.uniqueId = member.getMemberId();
        this.username = member.getUsername();
        this.role = member.getMemberRole().getKey();
    }

    @Builder
    public LoginDto(String uniqueId, String role){
        this.uniqueId = uniqueId;
        this.role = role;
    }
}