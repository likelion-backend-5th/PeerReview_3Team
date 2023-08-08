package com.likelion.MutsaMarket.repository;

import com.likelion.MutsaMarket.entity.CommentEntity;
import com.likelion.MutsaMarket.entity.NegotiationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegotiationRepository extends JpaRepository<NegotiationEntity, Long> {
}
