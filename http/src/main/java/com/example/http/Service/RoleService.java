package com.example.http.Service;

import com.example.http.payload.request.RoleRequest;
import com.example.http.payload.request.RoleUpdateRequest;
import com.example.http.payload.response.BaseResponse;

import javax.servlet.http.HttpServletRequest;

public interface RoleService {
    BaseResponse<?> addRole(RoleRequest roleRequest);
    BaseResponse<?> updateRole(RoleUpdateRequest roleUpdateRequest);
    BaseResponse<?> deleteRole(Long id);
}
