package com.cotato.squadus.domain.club.schedule.entity;

import com.cotato.squadus.domain.club.common.entity.Club;
import com.cotato.squadus.domain.club.common.entity.ClubAdminMember;
import com.cotato.squadus.domain.club.schedule.enums.ScheduleCategory;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "club_schedule")
public class ClubSchedule {

    @Id @GeneratedValue
    private Long scheduleIdx;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    private String title;

    private ScheduleCategory scheduleCategory;

    private String content;

    private LocalDateTime eventAt;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "clubSchedule", fetch = LAZY, cascade = ALL)
    private List<ScheduleComment> scheduleComments;

    @ManyToOne
    @JoinColumn(name = "club_member_idx")
    private ClubAdminMember author;

    private String location; // 추후에 Address 클래스로 바뀔 여지 있음

    private String equipment;

//    private List<Vote> votes;
//    private List<Participant> participants;

}
