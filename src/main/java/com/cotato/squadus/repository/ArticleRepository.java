package com.cotato.squadus.repository;

import com.cotato.squadus.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
