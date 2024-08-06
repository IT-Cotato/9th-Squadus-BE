package com.cotato.squadus.domain.club.common.entity;

import com.cotato.squadus.domain.auth.entity.Member;
import com.cotato.squadus.domain.auth.enums.Membership;
import com.cotato.squadus.domain.club.common.enums.MemberType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("MEMBER")
public class RegularClubMember extends ClubMember {

    @Builder
    public RegularClubMember(Member member, Club club, Membership membership, Boolean isPaid, String clubProfileImage) {
        super(member, club, membership, isPaid, clubProfileImage);
    }

    @Override
    public MemberType getMemberType() {
        return MemberType.MEMBER;
    }
}