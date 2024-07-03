package com.example.http.Service.impl;

import com.example.http.Model.Role;
import com.example.http.Model.RolePermission;
import com.example.http.Service.RoleService;
import com.example.http.payload.request.RoleRequest;
import com.example.http.payload.request.RoleUpdateRequest;
import com.example.http.payload.response.BaseResponse;
import com.example.http.payload.response.RoleResponse;
import com.example.http.repository.RolePermissionRepository;
import com.example.http.repository.RoleRepository;
import com.example.http.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public BaseResponse<?> addRole(RoleRequest roleRequest) {
        Role role = roleRepository.findByName(roleRequest.getName());

        if (role != null) {
            return new BaseResponse<>("ROLE.ERROR.EXIST");
        }
        Role newRole = new Role();
        newRole.setName(roleRequest.getName());
        roleRepository.save(newRole);

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(newRole.getId());
        rolePermission.setPermission("");
        rolePermissionRepository.save(rolePermission);

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(newRole.getId());
        roleResponse.setName(newRole.getName());

        return new BaseResponse<>("ROLE.SUCCESS.CREATE");


    }

    @Override
    public BaseResponse<?> updateRole(RoleUpdateRequest roleUpdateRequest) {
        Role roleCheckId = roleRepository.findById(roleUpdateRequest.getId()).orElse(null);
        if (roleCheckId == null) {
            return new BaseResponse<>("ROLE.ERROR.NOTEXIST");
        }
        Role roleCheckName = roleRepository.findByName(roleUpdateRequest.getName());
        if (roleCheckName != null) {
            return new BaseResponse<>("ROLE.ERROR.EXIST");
        }

        roleCheckId.setName(roleUpdateRequest.getName());
        roleRepository.save(roleCheckId);

        return new BaseResponse<>("ROLE.SUCCESS.UPDATE");
    }

    @Override
    public BaseResponse<?> deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            return new BaseResponse<>("ROLE.ERROR.NOTEXIST");
        }
        roleRepository.delete(role);
        RolePermission rolePermission = rolePermissionRepository.findRolePermissionByRoleId(role.getId());
        rolePermissionRepository.delete(rolePermission);
        return new BaseResponse<>("ROLE.SUCCESS.DELETE");
    }
}
