package com.example.http.Controller;

import com.example.http.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get-all-accounts-users")
    public ResponseEntity<?> getAllAccountsUsers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(adminService.getAllAccountsUsers(pageable));
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get-all-accounts-users/thread")
    public ResponseEntity<?> getAllAccountsUsersByThread(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(adminService.getAllAccountsUsersByThread(pageable));
    }
}
