package com.example.http.Service;

import com.example.http.Payload.request.RoleRequest;
import com.example.http.Payload.request.RoleUpdateRequest;
import com.example.http.Payload.response.BaseResponse;

public interface RoleService {
    BaseResponse<?> addRole(RoleRequest roleRequest);
    BaseResponse<?> updateRole(RoleUpdateRequest roleUpdateRequest);
    BaseResponse<?> deleteRole(Long id);
}
