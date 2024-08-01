package com.example.http.Controller;

import com.example.http.Service.RolePermissionService;
import com.example.http.Payload.request.RolePermissionUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/role-permission")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;
    @GetMapping("/gets")
    public ResponseEntity<Object> getAllPermission(){
        return ResponseEntity.ok(rolePermissionService.getAllPermission());
    }

    @GetMapping("/getPermission/{roleId}")
    public ResponseEntity<Object> getPermissionByRoleId(@PathVariable(name = "roleId") Long roleId){
        return ResponseEntity.ok(rolePermissionService.getPermissionByRoleId(roleId));
    }

    @GetMapping("/getPermissionByAccountId/{accountId}")
    public ResponseEntity<Object> getPermissionByAccountId(@PathVariable(name = "accountId") Long accountId){
        return ResponseEntity.ok(rolePermissionService.getPermissionByAccountId(accountId));
    }

    @GetMapping("/getPermissionOfThisAccount")
    public ResponseEntity<Object> getPermissionOfThisAccount(HttpServletRequest request){
        return ResponseEntity.ok(rolePermissionService.getPermission(request));
    }

    @PutMapping("/updateRolePermission")
    public ResponseEntity<Object> updateRolePermission(@Valid @RequestBody Map<String, String> data){
        // Find roleId key to set for an updateRolePermission
        RolePermissionUpdate rolePermissionUpdate = new RolePermissionUpdate();
        Map<String, String> Permission = new HashMap<>();
        data.forEach((k, v) -> {
            if (k.equals("roleId")) {
                rolePermissionUpdate.setRoleId(Long.valueOf(v));
            } else
                Permission.put(k, v);

        });

        rolePermissionUpdate.setPermission(Permission);
        return ResponseEntity.ok(rolePermissionService.updateRolePermission(rolePermissionUpdate));
    }
}
