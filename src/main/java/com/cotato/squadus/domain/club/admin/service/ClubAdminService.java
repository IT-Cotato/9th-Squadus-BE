package com.cotato.squadus.domain.club.admin.service;

import com.cotato.squadus.api.admin.dto.ClubJoinApprovalResponse;
import com.cotato.squadus.common.error.ErrorCode;
import com.cotato.squadus.common.error.exception.AppException;
import com.cotato.squadus.domain.auth.enums.Membership;
import com.cotato.squadus.domain.auth.service.ClubMemberService;
import com.cotato.squadus.domain.club.common.entity.ClubAdminMember;
import com.cotato.squadus.domain.club.common.entity.ClubApplication;
import com.cotato.squadus.domain.club.common.entity.ClubMember;
import com.cotato.squadus.domain.club.common.entity.RegularClubMember;
import com.cotato.squadus.domain.club.common.enums.MemberType;
import com.cotato.squadus.domain.club.common.repository.ClubApplicationRepository;
import com.cotato.squadus.domain.club.common.repository.ClubMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubAdminService {

    private final ClubApplicationRepository clubApplicationRepository;
    private final ClubMemberService clubMemberService;
    private final ClubMemberRepository clubMemberRepository;

    @Transactional
    public ClubJoinApprovalResponse approveApply(Long clubId, Long applicationId) {
        validateAdminMember(clubId);
        ClubApplication clubApplication = clubApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("해당 고유번호를 가진 지원서를 찾을 수 없습니다."));

        ClubMember clubMember = RegularClubMember.builder()
                .member(clubApplication.getMember())
                .club(clubApplication.getClub())
                .membership(Membership.JOINED)
                .isPaid(false)
                .build();

        ClubMember savedMember = clubMemberRepository.save(clubMember);
        return new ClubJoinApprovalResponse(savedMember.getClubMemberIdx());
    }

    private void validateAdminMember(Long clubId) {
        ClubMember clubMember = clubMemberService.findClubMemberBySecurityContextHolder();
        if (clubMember.getMemberType().equals(MemberType.MEMBER)) {
            throw new AppException(ErrorCode.MEMBER_TYPE_IS_NOT_ADMIN);
        }
    }

}
