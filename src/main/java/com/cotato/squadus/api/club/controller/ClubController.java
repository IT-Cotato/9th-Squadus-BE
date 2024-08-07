package com.cotato.squadus.api.club.controller;

import com.cotato.squadus.api.club.dto.*;
import com.cotato.squadus.domain.club.common.service.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리", description = "동아리 관련 API")
@RestController
@RequestMapping("/v1/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping()
    @Operation(summary = "동아리 생성", description = "동아리에 대한 정보를 바탕으로 동아리를 생성합니다")
    public ResponseEntity<ClubCreateResponse> createClub(@RequestBody ClubCreateRequest clubCreateRequest) {
        ClubCreateResponse clubCreateResponse = clubService.createClub(clubCreateRequest);
        return ResponseEntity.ok(clubCreateResponse);
    }

    @GetMapping("/{clubId}")
    @Operation(summary = "동아리 기본 정보 조회", description = "clubId를 바탕으로 동아리에 대한 정보를 반환합니다.")
    public ResponseEntity<ClubInfoResponse> findClubInfo(@PathVariable Long clubId) {
        ClubInfoResponse clubInfoResponse = clubService.findClubInfo(clubId);
        return ResponseEntity.ok(clubInfoResponse);
    }

    @PostMapping("/{clubId}")
    @Operation(summary = "동아리 가입 신청", description = "clubId와 동아리 가입에 대한 정보를 바탕으로 동아리 가입을 신청합니다")
    public ResponseEntity<ClubApplyResponse> joinClub(@PathVariable Long clubId, @RequestBody ClubApplyRequest clubApplyRequest) {
        ClubApplyResponse clubApplyResponse = clubService.joinClub(clubId, clubApplyRequest);
        return ResponseEntity.ok(clubApplyResponse);
    }
}
