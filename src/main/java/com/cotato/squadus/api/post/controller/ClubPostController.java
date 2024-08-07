package com.cotato.squadus.api.post.controller;

import com.cotato.squadus.api.post.dto.*;
import com.cotato.squadus.domain.auth.service.ClubMemberService;
import com.cotato.squadus.domain.club.post.service.ClubPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리 공지", description = "동아리 공지 관련 API")
@Slf4j
@RestController
@RequestMapping("/v1/api/clubs/{clubId}/posts")
@RequiredArgsConstructor
public class ClubPostController {

    private final ClubPostService clubPostService;
    private final ClubMemberService clubMemberService;

    @GetMapping("")
    @Operation(summary = "동아리 공지 전체 조회", description = "clubId를 바탕으로 동아리 공지 전체를 조회합니다")
    public ResponseEntity<ClubPostListResponse> getAllClubPostsByClubId(@PathVariable Long clubId) {
        clubMemberService.validateClubMember(clubId);
        ClubPostListResponse allClubPostsByClubId = clubPostService.findAllClubPostsByClubId(clubId);
        log.info("ClubId로 동아리 공지 전체 조회 : {} ", allClubPostsByClubId);
        return ResponseEntity.ok(allClubPostsByClubId);
    }

    @GetMapping("/summary")
    @Operation(summary = "동아리 공지 요약 전체 조회", description = "clubId를 바탕으로 동아리 공지에 대한 요약 전체를 조회합니다")
    public ResponseEntity<ClubPostSummaryListResponse> getAllClubPostsSummaryByClubId(@PathVariable Long clubId) {
        clubMemberService.validateClubMember(clubId);
        ClubPostSummaryListResponse allClubPostsSummaryByClubId = clubPostService.findAllClubPostsSummaryByClubId(clubId);
        log.info("ClubId로 동아리 공지 전체 조회(제목, 날짜) : {} ", allClubPostsSummaryByClubId);
        return ResponseEntity.ok(allClubPostsSummaryByClubId);
    }

    @GetMapping("{postId}")
    @Operation(summary = "동아리 공지 단건 조회", description = "postId를 바탕으로 동아리 공지 하나에 대한 정보를 조회합니다")
    public ResponseEntity<ClubPostResponse> getPostByPostId(@PathVariable Long clubId, @PathVariable Long postId) {
        clubMemberService.validateClubMember(clubId);
        ClubPostResponse clubPostByPostId = clubPostService.findClubPostByPostId(postId);
        log.info("PostId로 동아리 공지 조회 : {} ", clubPostByPostId);
        return ResponseEntity.ok(clubPostByPostId);
    }

    @PostMapping("")
    @Operation(summary = "동아리 공지 생성", description = "동아리 공지를 하나 생성합니다")
    public ResponseEntity<ClubPostCreateResponse> createClubPost(@PathVariable Long clubId, @RequestBody ClubPostCreateRequest clubPostCreateRequest) {
        clubMemberService.validateClubMember(clubId);
        ClubPostCreateResponse clubPostCreateResponse = clubPostService.createClubPost(clubId, clubPostCreateRequest);
        log.info("동아리 공지 작성, postId: {} ", clubPostCreateResponse);
        return ResponseEntity.ok(clubPostCreateResponse);
    }

    @PatchMapping("{postId}/like")
    @Operation(summary = "동아리 공지 좋아요 증가", description = "postId를 바탕으로 동아리 공지의 좋아요를 1 증가시킵니다")
    public ResponseEntity<ClubPostLikesResponse> increaseClubPostLikes(@PathVariable Long clubId, @PathVariable Long postId) {
        clubMemberService.validateClubMember(clubId);
        ClubPostLikesResponse clubPostLikesResponse = clubPostService.increaseClubPostLikes(postId);
        log.info("동아리 공지 좋아요, likes: {} ", clubPostLikesResponse);
        return ResponseEntity.ok(clubPostLikesResponse);
    }
}
