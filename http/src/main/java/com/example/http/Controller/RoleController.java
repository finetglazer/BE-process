package com.example.http.Controller;

import com.example.http.Service.RoleService;
import com.example.http.Payload.request.RoleRequest;
import com.example.http.Payload.request.RoleUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/permissiongroup")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAuthority('system_role_update')")
    @PostMapping("/create")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok(roleService.addRole(roleRequest));
    }

    @PreAuthorize("hasAuthority('system_role_update')")
    @PutMapping("/update")
    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleUpdateRequest roleUpdateRequest) {
        return ResponseEntity.ok(roleService.updateRole(roleUpdateRequest));
    }

    @PreAuthorize("hasAuthority('system_role_update')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(roleService.deleteRole(id));
    }
}
