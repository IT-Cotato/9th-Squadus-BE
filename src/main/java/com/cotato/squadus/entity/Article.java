package com.cotato.squadus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue
    private Long articleIdx;

    private String title;

    private String subtitle;

    private String type;

    private LocalDateTime createdAt;

    private String tag;

    private String content;

    private Long views;

    @Builder
    public Article(String title, String subtitle, String type, LocalDateTime createdAt, String tag, String content, Long views) {
        this.title = title;
        this.subtitle = subtitle;
        this.type = type;
        this.createdAt = createdAt;
        this.tag = tag;
        this.content = content;
        this.views = views;
    }

    public Article() {
    }
}
