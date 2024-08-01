package com.example.http.Payload.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AccountResponse {
    private Long Id;
    private String username;
    private String password;
    private LocalDateTime lastLogin;

    public AccountResponse(Long Id, String username, String password, LocalDateTime lastLogin) {
        this.Id = Id;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;

    }



}
