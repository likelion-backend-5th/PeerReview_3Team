package com.example.project_1_seohyunkim.repos;

import com.example.project_1_seohyunkim.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}