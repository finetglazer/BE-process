package com.example.http.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RoleUpdateRequest {
    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
}
