package com.example.http.Service;

import com.example.http.Payload.request.RolePermissionUpdate;
import com.example.http.Payload.response.BaseResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface RolePermissionService {
    Map<String, List<String>> getAllPermission();
    BaseResponse<?> getPermissionByRoleId(Long roleId);
    BaseResponse<?> getPermissionByAccountId(Long accountId);

    BaseResponse<?> getPermission(HttpServletRequest request);
    BaseResponse<?> updateRolePermission(RolePermissionUpdate rolePermissionUpdate);
}