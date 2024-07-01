package com.cotato.squadus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    private Long memberIdx;

    private String memberId;

    private String username; // 임시로 생성

    private String password; // 임시로 생성

    private String role; // 임시로 생성


    private String email;

    private String profileImage;

    private String university;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    // jwt refresh token
    @Column(length = 1000)
    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }

    @OneToMany(mappedBy = "member", fetch = LAZY, cascade = ALL)
    private List<ClubMember> clubMemberships; // 가입된 동아리 리스트 확인
}
