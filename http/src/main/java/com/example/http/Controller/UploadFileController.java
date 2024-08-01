package com.example.http.Controller;

import com.example.http.Service.UploadFileService;
import com.example.http.Payload.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
public class UploadFileController {

    @Autowired
    private UploadFileService uploadFileService;

    @PreAuthorize("hasAnyAuthority('book_write')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@ModelAttribute("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(new BaseResponse<>(uploadFileService.uploadFile(file)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAnyAuthority('book_write')")
    @PostMapping("/upload_multiple_files")
    public ResponseEntity<?> uploadMultipleFiles(@ModelAttribute("files") MultipartFile[] files) {
        try {
            return ResponseEntity.ok(new BaseResponse<>(uploadFileService.uploadMultipleFiles(files)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(new BaseResponse<>(uploadFileService.deleteFile(filename)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
