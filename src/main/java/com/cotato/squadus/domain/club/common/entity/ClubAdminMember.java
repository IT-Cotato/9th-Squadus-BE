package com.cotato.squadus.domain.club.common.entity;

import com.cotato.squadus.domain.auth.entity.Member;
import com.cotato.squadus.domain.auth.enums.AdminStatus;
import com.cotato.squadus.domain.auth.enums.Membership;
import com.cotato.squadus.domain.club.common.enums.MemberType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("ADMIN")
@NoArgsConstructor
public class ClubAdminMember extends ClubMember {

    @Enumerated(EnumType.STRING)
    private AdminStatus adminStatus;

    @Builder
    public ClubAdminMember(Member member, Club club, Membership membership, Boolean isPaid, String clubProfileImage, AdminStatus adminStatus) {
        super(member, club, membership, isPaid, clubProfileImage);
        this.adminStatus = adminStatus;
    }

    @Override
    public MemberType getMemberType() {
        return MemberType.ADMIN;
    }

}
