package com.example.project_1_seohyunkim.repos;

import com.example.project_1_seohyunkim.entity.SalesItemEntity;
import com.example.project_1_seohyunkim.mapping.ItemInfoMapping;
import com.example.project_1_seohyunkim.mapping.ItemsMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesItemRepository extends JpaRepository<SalesItemEntity, Long> {
    Page<ItemsMapping> findAllProjectedBy(Pageable pageable);

    Optional<ItemInfoMapping> findProjectedById(Long id);

 }
