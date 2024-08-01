package com.example.http.Payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionUpdate {
    @NotNull
    private Long roleId;
    @NotNull
    private Map<String, String> permission;
}
