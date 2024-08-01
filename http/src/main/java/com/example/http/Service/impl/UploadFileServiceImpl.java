package com.example.http.Service.impl;

import com.example.http.Service.UploadFileService;
import com.example.http.Payload.response.BaseResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @Value("http://localhost:9090")
    private String minioUrl;

    @Override
    public BaseResponse<?> uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please provide a file to upload");
        }

        String fileExtension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        if (!isAllowedFileType(fileExtension)) {
            throw new IllegalArgumentException("Invalid file type");
        }

        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .contentType(this.getContentType(fileExtension))
                    .object(file.getOriginalFilename())
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
            return new BaseResponse<>(minioUrl + "/" + bucketName + "/" + file.getOriginalFilename());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public BaseResponse<?> uploadMultipleFiles(MultipartFile[] files) {
        if (files == null) {
            throw new IllegalArgumentException("Please provide a file to upload");
        }

        Map<String, String> res = new TreeMap<>();
        Stream.of(files).forEach(k -> {
            uploadFile(k);
            res.put(k.getOriginalFilename(), minioUrl + "/" + bucketName + "/" + k.getOriginalFilename());
        });
        return new BaseResponse<>(res
        );
    }

    @Override
    public BaseResponse<?> deleteFile(String filename) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(filename).build());
            minioClient.removeObject(RemoveObjectArgs
                    .builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
            return new BaseResponse<>("File deleted successfully");
        } catch (Exception e) {
            return new BaseResponse<>("File does not exist");
//            throw new RuntimeException(e);
        }
    }

//    private String getMinioPath(String objectName) {
//        return minioUrl + bucketName + "/" + objectName;
//    }

    private String getContentType(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();

        if (lowerCaseFileName.endsWith("png") || lowerCaseFileName.endsWith("jpg") || lowerCaseFileName.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (lowerCaseFileName.endsWith("mp4") || lowerCaseFileName.endsWith("avi") || lowerCaseFileName.endsWith("mkv")) {
            return "video/mp4";
        } else if (lowerCaseFileName.endsWith("mp3") || lowerCaseFileName.endsWith("wav") || lowerCaseFileName.endsWith("flac")) {
            return "audio/mp3";
        } else if (lowerCaseFileName.endsWith("pdf")) {
            return "application/pdf";
        } else if (lowerCaseFileName.endsWith("xlsx") || lowerCaseFileName.endsWith("xls")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (lowerCaseFileName.endsWith("docx") || lowerCaseFileName.endsWith("doc")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else {
            return "application/octet-stream";
        }
    }

    public boolean isAllowedFileType(String fileExtension) {
        List<String> allowedFileTypes = List.of("jpg", "jpeg", "png", "pdf", "mp4", "mp3", "xlsx", "xls", "docx", "doc", "java");
        return allowedFileTypes.contains(fileExtension.toLowerCase());
    }

    public String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1);
    }
}
