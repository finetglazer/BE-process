package com.example.http.Service;

import com.example.http.Payload.response.BaseResponse;

public interface GoogleDriveService {
    BaseResponse<?> getInfo(String token);
    BaseResponse<?> getApps(String token);
    BaseResponse<?> getDrives(String token);
    BaseResponse<?> createDrive(String token, String name);
}
