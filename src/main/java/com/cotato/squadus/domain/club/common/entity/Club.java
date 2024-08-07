package com.cotato.squadus.domain.club.common.entity;

import com.cotato.squadus.common.entity.BaseTimeEntity;
import com.cotato.squadus.domain.club.common.enums.ClubTier;
import com.cotato.squadus.domain.club.common.enums.SportsCategory;
import com.cotato.squadus.domain.club.post.entity.ClubPost;
import com.cotato.squadus.domain.club.schedule.entity.ClubSchedule;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "club")
@EntityListeners(AuditingEntityListener.class)
public class Club extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    private String clubName;

    private String university;

    private ClubTier clubTier;

    private Integer clubRank;

    @Lob
    private String clubMessage;

    private Long maxMembers;

    private Integer numberOfMembers;

    @ElementCollection
    private List<String> tags; // 별도의 테이블을 생성하여 컬렉션의 데이터를 저장

    @Enumerated(EnumType.STRING)
    private SportsCategory sportsCategory;

    //s3로 이미지 저장
    private String logo;

    @OneToMany(mappedBy = "club", cascade = ALL)
    private List<ClubSchedule> clubSchedules;

    @OneToMany(mappedBy = "club", cascade = ALL)
    private List<ClubMember> clubMembers;

    @OneToMany(mappedBy = "club", fetch = LAZY, cascade = ALL)
    private List<ClubPost> clubPosts;

    @Builder
    private Club(String clubName, String university, SportsCategory sportsCategory, String logo, ClubTier clubTier, Integer clubRank, String clubMessage, Long maxMembers) {
        this.clubName = clubName;
        this.university = university;
        this.sportsCategory = sportsCategory;
        this.logo = logo;
        this.clubTier = clubTier;
        this.clubRank = clubRank;
        this.clubMessage = clubMessage;
        this.maxMembers = maxMembers;
        this.numberOfMembers = 0;
    }

    public void addClubMember(ClubMember clubMember) {
        this.clubMembers.add(clubMember);
    }

    public void addNumberOfMembers() {
        this.numberOfMembers++;
    }
}
