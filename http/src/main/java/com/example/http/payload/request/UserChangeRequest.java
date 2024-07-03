package com.example.http.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserChangeRequest {
    @NotNull
    private Long id;

    private String username;
    private Long roleId;
    private String name;
}
