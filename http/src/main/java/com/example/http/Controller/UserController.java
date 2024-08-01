package com.example.http.Controller;

import com.example.http.Service.UserService;
import com.example.http.Payload.request.UserChangeRequest;
import com.example.http.Payload.request.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('system_user_update')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // delete user
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PreAuthorize("hasAuthority('system_user_view')")
    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUser(@PageableDefault Pageable pageable,
                                        @RequestParam(name = "search", defaultValue = "", required = false) String Search,
                                        @RequestParam(name = "role_id", defaultValue = "0", required = false) Long RoleId) {
        // get all user
        return ResponseEntity.ok(userService.getAllUser(pageable, Search, RoleId));
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/getAllLeaders")
    public ResponseEntity<?> getAllLeaders(@PageableDefault Pageable pageable) {
        // get all user
        return ResponseEntity.ok(userService.getAllLeaders(pageable));
    }

    @PreAuthorize("hasAuthority('system_user_update_role')")
    @PutMapping("/updateRole")
    public ResponseEntity<?> updateRoleUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.updateRole(userUpdateRequest));
    }

    @PreAuthorize("hasAuthority('system_user_update')")
    @PutMapping("/changeUser")
    public ResponseEntity<?> changeUser(@Valid @RequestBody UserChangeRequest userChangeRequest) {
        return ResponseEntity.ok(userService.changeUser(userChangeRequest));
    }
}

