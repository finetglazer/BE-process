package com.example.http.Payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String name;
    private Long roleId;

    public UserResponse(String username, String name, Long roleId) {
        this.username = username;
        this.name = name;
        this.roleId = roleId;
    }
}
