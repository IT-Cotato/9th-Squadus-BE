package com.cotato.squadus.api.admin.controller;

import com.cotato.squadus.api.admin.dto.ClubJoinApprovalResponse;
import com.cotato.squadus.domain.club.admin.service.ClubAdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리 관리", description = "동아리 관리 관련 API")
@RestController
@RequestMapping("/v1/api/clubs/{clubId}/admin")
@RequiredArgsConstructor
public class ClubAdminController {

    private final ClubAdminService clubAdminService;

    @PostMapping("/approval/{applicationId}")
    public ResponseEntity<ClubJoinApprovalResponse> approveClubMember(@PathVariable Long clubId, @PathVariable Long applicationId) {
        ClubJoinApprovalResponse clubJoinApprovalResponse = clubAdminService.approveApply(clubId, applicationId);
        return ResponseEntity.ok(clubJoinApprovalResponse);
    }
}
