package com.example.http.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RefreshTokenRequest {
    @NotBlank
    private String Token;
    @NotNull
    private Long userId;
}
