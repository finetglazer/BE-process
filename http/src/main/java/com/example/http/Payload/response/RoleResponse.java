package com.example.http.Payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;

    public RoleResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
