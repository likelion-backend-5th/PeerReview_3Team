package com.likelion.MutsaMarket.repository;

import com.likelion.MutsaMarket.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
