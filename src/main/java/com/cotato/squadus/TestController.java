package com.cotato.squadus;

import com.cotato.squadus.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @PostMapping
    public void group(@RequestBody @Valid ArticleDto articleDto){
        articleService.createArticle(articleDto);
    }

    @GetMapping(value = "/{articleIdx}", produces = "application/json")
    public ArticleDto get(@PathVariable Long articleIdx){
        return articleService.getArticle(articleIdx);
    }
}

