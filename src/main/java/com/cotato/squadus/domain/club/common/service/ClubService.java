package com.cotato.squadus.domain.club.common.service;

import com.cotato.squadus.domain.auth.repository.MemberRepository;
import com.cotato.squadus.domain.club.common.enums.ClubTier;
import com.cotato.squadus.domain.club.common.enums.SportsCategory;
import com.cotato.squadus.domain.club.common.repository.ClubApplicationRepository;
import com.cotato.squadus.domain.club.common.repository.ClubRepository;
import com.cotato.squadus.api.club.dto.ClubCreateRequest;
import com.cotato.squadus.api.club.dto.ClubCreateResponse;
import com.cotato.squadus.api.club.dto.ClubApplyRequest;
import com.cotato.squadus.api.club.dto.ClubApplyResponse;
import com.cotato.squadus.domain.auth.enums.ApplicationStatus;
import com.cotato.squadus.domain.club.common.entity.Club;
import com.cotato.squadus.domain.club.common.entity.ClubApplication;
import com.cotato.squadus.domain.auth.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final ClubApplicationRepository clubApplicationRepository;

    /**
     *
     * @param clubCreateRequest 동아리 생성 dto
     * @return ClubCreateResponse 생성된 동아리 id
     */
    @Transactional
    public ClubCreateResponse createClub(ClubCreateRequest clubCreateRequest) {
        Club club = Club.builder()
                .clubName(clubCreateRequest.getClubName())
                .university(clubCreateRequest.getUniversity())
                .sportsCategory(SportsCategory.valueOf(clubCreateRequest.getSportsCategory()))
                .logo(clubCreateRequest.getLogo())
                .clubTier(ClubTier.BRONZE)
                .build();

        Club savedClub = clubRepository.save(club);
        log.info("Club created : {}", savedClub);
        return new ClubCreateResponse(savedClub.getClubId());
    }

    /**
     *
     * @param clubApplyRequest 동아리 가입 신청 dto
     * @return ClubApplyResponse clubApplication id 담아서 리턴
     * 동아리 가입 신청하는 매서드입니다.
     * 초기에는 상태 PENDING으로 설정되고 나중에 동아리장이 가입 승인하면 바뀜
     */
    @Transactional
    public ClubApplyResponse joinClub(Long clubId, ClubApplyRequest clubApplyRequest) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("해당 고유번호를 가진 동아리를 찾을 수 없습니다."));
        Member member = memberRepository.findById(clubApplyRequest.getMemberIdx())
                .orElseThrow(() -> new EntityNotFoundException("해당 고유번호를 가진 회원을 찾을 수 없습니다."));
        ClubApplication clubApplication = ClubApplication.builder()
                .member(member)
                .club(club)
                .appliedAt(LocalDateTime.now())
                .applicationStatus(ApplicationStatus.PENDING)
                .build();
        ClubApplication savedApplication = clubApplicationRepository.save(clubApplication);
        return new ClubApplyResponse(savedApplication.getApplicationIdx());
    }

}
