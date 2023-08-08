package com.example.project_2_seohyunkim.repos;

import com.example.project_2_seohyunkim.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}