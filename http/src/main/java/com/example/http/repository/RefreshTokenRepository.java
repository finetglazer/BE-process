package com.example.http.repository;

import com.example.http.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
//    Boolean existsByUserId(Long userId);
    RefreshToken findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
