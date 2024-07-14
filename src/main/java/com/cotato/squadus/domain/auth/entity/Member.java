package com.cotato.squadus.domain.auth.entity;

import com.cotato.squadus.domain.club.common.entity.ClubAdminMember;
import com.cotato.squadus.domain.club.common.entity.ClubMember;
import com.cotato.squadus.domain.auth.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long memberIdx;

    private String uniqueId;

//    private String memberId;

    private String username; // 임시로 생성

//    private String password; // 임시로 생성

//    private String role; // 임시로 생성


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


    @Builder
    public Member(String uniqueId, String username, String email, String memberRole) {
        this.uniqueId = uniqueId;
        this.username = username;
        this.email = email;
        if (memberRole.equals("MEMBER")) this.memberRole = MemberRole.MEMBER;
        else if(memberRole.equals("CERTIFIED_MEMBER")) this.memberRole = MemberRole.CERTIFIED_MEMBER;
    }

    public Member update(String email, String username) {
        this.email = email;
        this.username = username;
        return this;
    }

}
