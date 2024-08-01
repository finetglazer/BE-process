package com.example.http.Service;

import com.example.http.Payload.response.BaseResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    BaseResponse<?> uploadFile(@ModelAttribute("file") MultipartFile file);
    BaseResponse<?> uploadMultipleFiles(@ModelAttribute("files") MultipartFile[] files);
    BaseResponse<?> deleteFile(String filename);

}
