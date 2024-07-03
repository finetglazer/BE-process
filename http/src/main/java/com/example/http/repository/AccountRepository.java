package com.example.http.repository;

import com.example.http.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountById(Long id);

    Optional<Account> findAccountByUsername(String userName);

    @Query(value = "SELECT a FROM Account a WHERE a.username = :userName")
    Account nghichNgu(String userName);

    Boolean existsByUsername(String username);
}
