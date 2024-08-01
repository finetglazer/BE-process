package com.example.http.Payload.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserUpdateRequest {
    @NotNull
    private Long id;

    @NotNull
    @Min(2)
    private Long Updateid;
}
