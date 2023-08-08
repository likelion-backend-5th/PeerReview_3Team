package com.example.project_2_seohyunkim.service;

import com.example.project_2_seohyunkim.BaseResponse;
import com.example.project_2_seohyunkim.ResponseMessage;
import com.example.project_2_seohyunkim.dto.ArticleDto;
import com.example.project_2_seohyunkim.entity.ArticleEntity;
import com.example.project_2_seohyunkim.entity.LikeArticleEntity;
import com.example.project_2_seohyunkim.entity.UserEntity;
import com.example.project_2_seohyunkim.jwt.JwtTokenUtils;
import com.example.project_2_seohyunkim.repos.ArticleRepository;
import com.example.project_2_seohyunkim.repos.LikeArticleRepository;
import com.example.project_2_seohyunkim.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeArticleService {
    private final LikeArticleRepository likeArticleRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final PasswordEncoder passwordEncoder;

    public void likeArticle(Long articleId, String username, String password) {
        Optional<ArticleEntity> article = articleRepository.findById(articleId);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (article.isPresent()) {
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                ArticleEntity articleEntity = article.get();
                if (passwordEncoder.matches(password, userEntity.getPassword()) &&
                    !username.equals(articleEntity.getUser().getUsername())) { // 피드 작성자가 본인인지 확인
                    Optional<LikeArticleEntity> likeArticle = likeArticleRepository.findByArticleIdAndUserId(articleId, userEntity.getId());
                    if (likeArticle.isPresent()) { // 피드에 이미 좋아요 했으면
                        LikeArticleEntity likeArticleEntity = likeArticle.get();
                        likeArticleRepository.deleteById(likeArticleEntity.getId()); // 좋아요 취소
                    } else {
                        LikeArticleEntity newLike = new LikeArticleEntity();
                        newLike.setArticle(article.get());
                        newLike.setUser(user.get());
                        likeArticleRepository.save(newLike);
                    }
                } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't like your feed yourself");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found");
    }
}
