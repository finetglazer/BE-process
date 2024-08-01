package com.example.http.Controller;

import com.example.http.Service.AccountService;
import com.example.http.Payload.request.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasAuthority('system_user_update')")
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest request) {
        return ResponseEntity.ok(accountService.changePassword(changePasswordRequest, request));
    }

    @PreAuthorize("hasAuthority('system_user_view')")
    @GetMapping("/get-all-accounts")
    public ResponseEntity<?> getAllAccounts(@PageableDefault Pageable pageable,
                                            @RequestParam(name = "search", defaultValue = "", required = false) String search) {
        return ResponseEntity.ok(accountService.getAllAccounts(pageable, search));
    }
}
