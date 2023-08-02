package com.likelion.MutsaMarket.repository;

import com.likelion.MutsaMarket.entity.SalesItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesItemRepository extends JpaRepository<SalesItemEntity, Long> {
}