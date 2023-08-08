package com.likelion.MutsaMarket.repository;

import com.likelion.MutsaMarket.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 1. 사용자 계정이름으로 사용자 정보를 회수하는 기능
    Optional<UserEntity> findByUsername(String username);
    // 2. 사용자 계정이름을 가진 사용자 정보가 존재하는지 판단하는 기능
    boolean existsByUsername(String username);
}
