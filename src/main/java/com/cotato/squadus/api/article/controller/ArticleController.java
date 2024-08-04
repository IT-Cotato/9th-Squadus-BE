package com.cotato.squadus.api.article.controller;

import com.cotato.squadus.api.article.dto.ArticleListResponse;
import com.cotato.squadus.api.article.dto.ArticleRequest;
import com.cotato.squadus.api.article.dto.ArticleResponse;
import com.cotato.squadus.api.article.dto.ArticleSummaryResponse;
import com.cotato.squadus.domain.club.article.service.ArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Long articleId) {
        ArticleResponse article = articleService.findArticleById(articleId);
        log.info("ArticleId로 기사 조회 : {} ", article);
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(
            @RequestPart("articleRequest") String articleRequestString,
            @RequestPart("image") MultipartFile imageFile) {
        // JSON String을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleRequest articleRequest;
        try {
            articleRequest = objectMapper.readValue(articleRequestString, ArticleRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid JSON format", e);
        }

        ArticleResponse article = articleService.createArticle(articleRequest, imageFile);
        log.info("새 기사 생성 : {} ", article);
        return ResponseEntity.ok(article);
    }

    @GetMapping
    public ResponseEntity<Page<ArticleSummaryResponse>> getAllArticleSummaries(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ArticleSummaryResponse> articles = articleService.findAllArticleSummaries(pageable);
        log.info("모든 기사 요약 조회, 페이지 정보 : {} ", pageable);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/all")
    public ResponseEntity<ArticleListResponse> getAllArticles() {
        List<ArticleSummaryResponse> articles = articleService.getAllArticles();
        log.info("모든 기사 요약 조회");
        return ResponseEntity.ok(ArticleListResponse.from(articles));
    }
}
