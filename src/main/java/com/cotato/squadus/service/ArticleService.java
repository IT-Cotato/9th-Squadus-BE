package com.cotato.squadus.service;

import com.cotato.squadus.ArticleDto;
import com.cotato.squadus.entity.Article;
import com.cotato.squadus.repository.ArticleRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void createArticle(ArticleDto articleDto){
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .subtitle(articleDto.getSubtitle())
                .type(articleDto.getType())
                .createdAt(articleDto.getCreatedAt())
                .tag(articleDto.getTag())
                .content(articleDto.getContent())
                .views(articleDto.getViews())
                .build();
        try{
            articleRepository.save(article);
        }catch(DataIntegrityViolationException e){
            throw new InvalidRequestStateException();
        }
    }

    public ArticleDto getArticle(Long id){
        Article article = articleRepository.findById(id)
                .orElseThrow();
        return ArticleDto.builder()
                .title(article.getTitle())
                .subtitle(article.getSubtitle())
                .type(article.getType())
                .createdAt(article.getCreatedAt())
                .tag(article.getTag())
                .content(article.getContent())
                .views(article.getViews())
                .build();
    }

}
