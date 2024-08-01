package com.example.http.Controller;

import com.example.http.Service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/google_drive")
public class GoogleDriveController {
    @Autowired
    private GoogleDriveService googleDriveService;

    @GetMapping("/getInfo")
    ResponseEntity<?> getInfo(@RequestParam String token) {
        return ResponseEntity.ok().body(googleDriveService.getInfo(token));
    }

    @GetMapping("/getApps")
    ResponseEntity<?> getApps(@RequestParam String token) {
        return ResponseEntity.ok().body(googleDriveService.getApps(token));
    }

    @GetMapping("/getDrives")
    ResponseEntity<?> getDrives(@RequestParam String token) {
        return ResponseEntity.ok().body(googleDriveService.getDrives(token));
    }

    @GetMapping("/createDrive")
    ResponseEntity<?> createDrive(@RequestParam String token, @RequestParam String name) {
        return ResponseEntity.ok().body(googleDriveService.createDrive(token, name));
    }

}
