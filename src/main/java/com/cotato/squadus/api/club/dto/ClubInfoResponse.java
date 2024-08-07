package com.cotato.squadus.api.club.dto;

import com.cotato.squadus.domain.club.common.entity.Club;

import java.time.LocalDateTime;
import java.util.List;

public record ClubInfoResponse(
        Long id,
        String clubName,
        String university,
        String clubTier,
        Integer clubRank,
        String sportsCategory,
        String logo,
        Integer numberOfMembers,
        LocalDateTime createdAt,
        String clubMessage,
        List<String> tags
) {
    public static ClubInfoResponse from(Club club) {
        return new ClubInfoResponse(
                club.getClubId(),
                club.getClubName(),
                club.getUniversity(),
                club.getClubTier().name(),
                club.getClubRank(),
                club.getSportsCategory().name(),
                club.getLogo(),
                club.getClubMembers() != null ? club.getClubMembers().size() : 0,
                club.getCreatedAt(),
                club.getClubMessage(),
                club.getTags()
        );
    }
}
