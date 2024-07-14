package com.cotato.squadus.domain.auth.entity;

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
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    private Long memberIdx;

    private String memberId;

    private String username;

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
    public Member(String memberId, String username, String email, MemberRole memberRole) {
        this.memberId = memberId;
        this.username = username;
        this.email = email;
        this.memberRole = memberRole;
    }

    public Member update(String email, String name) {
        this.email = email;
        this.username = name;
        return this;
    }

    public String getRoleKey(){
        return this.memberRole.getKey();
    }
}
