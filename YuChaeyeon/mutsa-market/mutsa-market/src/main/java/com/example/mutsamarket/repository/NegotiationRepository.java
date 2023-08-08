package com.example.mutsamarket.repository;

import com.example.mutsamarket.entity.CommentEntity;
import com.example.mutsamarket.entity.NegotiationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegotiationRepository extends JpaRepository<NegotiationEntity, Long> {
}
