package com.example.http.Service.impl;

import com.example.http.Model.Account;
import com.example.http.Repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        try {
            return UserDetailsImpl.build(account);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public Account findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    public void saveLastLoginByUsername(Account account) {
        account.setLastLogin(LocalDateTime.now());
        accountRepository.save(account);
    }
}
