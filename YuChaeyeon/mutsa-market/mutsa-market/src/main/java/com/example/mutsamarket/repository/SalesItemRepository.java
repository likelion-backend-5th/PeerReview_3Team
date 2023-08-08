package com.example.mutsamarket.repository;

import com.example.mutsamarket.entity.SalesItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesItemRepository extends JpaRepository<SalesItemEntity, Long> {
}
