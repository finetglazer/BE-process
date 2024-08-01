package com.example.http.Repository;

import com.example.http.Model.User;
import com.example.http.Payload.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);

    @Query(value = "SELECT new com.example.http.Payload.response.UserResponse(u.username, u.name, u.roleId) FROM User u WHERE " +
    "u.username LIKE %:search% OR u.name LIKE %:search% OR u.roleId = :role_id " +
            "ORDER BY u.roleId ASC")
    Page<UserResponse> findAllByKeyword(Pageable pageable, @Param("search") String Search, @Param("role_id") Long RoleId);

    @Query(value = "SELECT new com.example.http.Payload.response.UserResponse(u.username, u.name, u.roleId) FROM User u WHERE " +
            "(u.username LIKE %:search% OR u.name LIKE %:search%) AND u.roleId = :role_id " +
            "ORDER BY u.roleId ASC")
    Page<UserResponse> findAllByKeywordAndNumber(Pageable pageable, @Param("search") String Search, @Param("role_id") Long RoleId);

    @Query(value = "SELECT new com.example.http.Payload.response.UserResponse(u.username, u.name, u.roleId) FROM User u WHERE " +
            "u.roleId = 2 " +
            "ORDER BY u.roleId ASC")
    Page<UserResponse> findAllLeaders(Pageable pageable);

    Optional<User> findById(Long id);

    @Query(value = "SELECT new com.example.http.Payload.response.UserResponse(u.username, u.name, u.roleId) FROM User u")
    Page<UserResponse> findAllUsers(Pageable pageable);
}
