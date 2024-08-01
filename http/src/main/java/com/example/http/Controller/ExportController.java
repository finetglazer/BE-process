package com.example.http.Controller;

import com.example.http.Helper.Word.CreateWord;
import com.example.http.Service.ExportService;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/export")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @Autowired
    private CreateWord createWord;

    @PreAuthorize("hasAnyAuthority('book_export')")
    @GetMapping("/export-book-word/{id}")
    public ResponseEntity<?> exportBook(@PathVariable Long id) {
        return ResponseEntity.ok(exportService.handleExportBookWord(id));
    }

    @PreAuthorize("hasAnyAuthority('book_export')")
    @GetMapping("/export-book-pdf/{id}")
    public ResponseEntity<?> exportBookPdf(@PathVariable Long id) {
        return ResponseEntity.ok(exportService.handleExportBookPdf(id));
    }

//    @PreAuthorize("hasAnyAuthority('book_export')")
//    @PostMapping("/upload-book-pdf/{id}")
//    public ResponseEntity<?> exportBookByFile(@PathVariable Long id) {
//        return ResponseEntity.ok(exportService.handleUploadBookPdf(id));
//    }

    @GetMapping("/export-word")
    public ResponseEntity<?> exportWord() {
        return ResponseEntity.ok(exportService.handleExportWord());
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<?> exportPdf() {
        return ResponseEntity.ok(exportService.handleExportPdf());
    }

    @GetMapping("/save-word")
    public ResponseEntity<?> saveWord(String encodedString) {
        return ResponseEntity.ok(exportService.handleSaveWord(encodedString));
    }

    @GetMapping("/export-word-by-file")
    public ResponseEntity<?> exportWordByFile(){


        try {
            File file = new File(createWord.createRawWordByFile());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error when create word");
        }

    }
}
