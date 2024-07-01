package com.cotato.squadus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDto {

    private String title;
    private String subtitle;
    private String type;
    private LocalDateTime createdAt;
    private String tag;
    private String content;
    private Long views;

    @Builder
    public ArticleDto(String title, String subtitle, String type, LocalDateTime createdAt, String tag, String content, Long views) {
        this.title = title;
        this.subtitle = subtitle;
        this.type = type;
        this.tag = tag;
        this.content = content;
        this.views = views;
    }
}
