package com.cotato.squadus.api.schedule.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ClubScheduleRequest {
    private String title;
    private String scheduleCategory;
    private String content;
    private Long authorId;
    private String location;
    private String equipment;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}