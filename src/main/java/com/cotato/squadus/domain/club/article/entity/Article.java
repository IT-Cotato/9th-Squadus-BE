package com.cotato.squadus.domain.club.article.entity;

import com.cotato.squadus.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "article")
@EntityListeners(AuditingEntityListener.class)
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleIdx;

    private String title;

    private String subtitle;

    private String type;

    private String tag;

    private String content;

    private Long views;

    @Builder
    public Article(String title, String subtitle, String type, String tag, String content, Long views) {
        this.title = title;
        this.subtitle = subtitle;
        this.type = type;
        this.tag = tag;
        this.content = content;
        this.views = views;
    }

    public Article() {
    }

    public void setViews(Long views) {
        this.views = views;
    }

}
