package com.example.market.service;

import com.example.market.dto.ArticleDto;
import com.example.market.entity.ArticleEntity;
import com.example.market.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleDto createArticle(ArticleDto dto) {
        ArticleEntity newArticle = new ArticleEntity();
        newArticle.setTitle(dto.getTitle());
        newArticle.setWantedPrice(dto.getWantedPrice());
        newArticle.setWriter(dto.getWriter());
        newArticle.setDescription(dto.getDescription());
        newArticle.setPassword(dto.getPassword());
        return ArticleDto.fromEntity(repository.save(newArticle));
    }

    public ArticleDto readArticle(Long id) {
        Optional<ArticleEntity> optionalArticle
            = repository.findById(id);
        if (optionalArticle.isPresent())
            return ArticleDto.fromEntity(optionalArticle.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public List<ArticleDto> readArticleAll() {
        List<ArticleDto> articleList = new ArrayList<>();
        for (ArticleEntity entity: repository.findAll()) {
            articleList.add(ArticleDto.fromEntity(entity));
        }
        return articleList;
    }

    public ArticleDto updateArticle(Long id, String password, ArticleDto dto) {
        Optional<ArticleEntity> optionalArticle
                = repository.findById(id);
        if (optionalArticle.isPresent()) {
            ArticleEntity articleEntity = optionalArticle.get();
            if(!articleEntity.getPassword().equals(password)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
            }else {
                ArticleEntity article = optionalArticle.get();
                article.setWriter(dto.getWriter());
                article.setTitle(dto.getTitle());
                article.setWantedPrice(dto.getWantedPrice());
                article.setDescription(dto.getDescription());
                article.setPassword(dto.getPassword());
                repository.save(article);
                return ArticleDto.fromEntity(article);

            }
        }else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void deleteArticle(Long id) {
        if (repository.existsById(id))
            repository.deleteById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}










