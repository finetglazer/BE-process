package com.example.http.Repository;

import com.example.http.Model.Account;
import com.example.http.Payload.response.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT new com.example.http.Payload.response.AccountResponse(a.id, a.username, a.password, a.lastLogin) FROM Account a WHERE a.username LIKE %:search%" +
            " OR a.password LIKE %:search% " +
            "ORDER BY a.id ASC")
    Page<AccountResponse> findAllAccountsByKeyword(Pageable pageable, String search);

    @Query(value = "SELECT new com.example.http.Payload.response.AccountResponse(a.id, a.username, a.password, a.lastLogin) FROM Account a")
    Page<AccountResponse> findAllAccounts(Pageable pageable);
}
