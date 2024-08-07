package com.cotato.squadus.domain.club.common.entity;
import com.cotato.squadus.domain.auth.entity.Member;
import com.cotato.squadus.domain.auth.enums.Membership;
import com.cotato.squadus.domain.club.common.enums.MemberType;
import com.cotato.squadus.domain.club.schedule.entity.ScheduleComment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "member_type")
@NoArgsConstructor
@Table(name = "club_member")
public abstract class ClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubMemberIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Enumerated(EnumType.STRING)
    private Membership membership;

    private Boolean isPaid;

    //s3로 이미지 url 저장
    private String clubProfileImage;

    @OneToMany(mappedBy = "clubMember", cascade = ALL)
    private List<ScheduleComment> scheduleComments;

    public abstract MemberType getMemberType();

    public ClubMember(Member member, Club club, Membership membership, Boolean isPaid, String clubProfileImage) {
        this.member = member;
        this.club = club;
        this.membership = membership;
        this.isPaid = isPaid;
        this.clubProfileImage = clubProfileImage;
    }
}
