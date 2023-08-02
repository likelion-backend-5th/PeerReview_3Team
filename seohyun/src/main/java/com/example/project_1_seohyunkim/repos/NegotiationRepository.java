package com.example.project_1_seohyunkim.repos;

import com.example.project_1_seohyunkim.entity.NegotiationEntity;
import com.example.project_1_seohyunkim.mapping.NegotiationsMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NegotiationRepository extends JpaRepository<NegotiationEntity, Long> {

    Page<NegotiationsMapping> findAllProjectedByItemId(Long itemId, Pageable pageable);
//    Page<NegotiationsMapping> findAllProjectedByItemIdAndWriter(Long itemId, String writer, Pageable pageable);
    Optional<NegotiationEntity> findByIdAndItemId(Long id, Long itemId);
//    List<NegotiationEntity> findByItemIdAndWriter(Long itemId, String writer);

    List<NegotiationEntity> findByItemId(Long itemId);


}
