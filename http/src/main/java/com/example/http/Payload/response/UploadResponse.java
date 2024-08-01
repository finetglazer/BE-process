package com.example.http.Payload.response;

import lombok.Data;

@Data
public class UploadResponse {
    private String url;
    private String fileName;

    public UploadResponse(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }
}
