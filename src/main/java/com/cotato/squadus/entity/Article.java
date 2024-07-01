package com.cotato.squadus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
