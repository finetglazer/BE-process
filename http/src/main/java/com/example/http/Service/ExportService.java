package com.example.http.Service;

import com.example.http.Payload.response.BaseResponse;
import com.fasterxml.jackson.databind.ser.Serializers;

public interface ExportService {
    BaseResponse<?> handleExportWord();
    BaseResponse<?> handleExportWordByFile();
    BaseResponse<?> handleExportPdf();
    BaseResponse<?> handleSaveWord(String encodedString);
    BaseResponse<?> handleExportBookWord(Long id);
    BaseResponse<?> handleExportBookPdf(Long id);
//    BaseResponse<?> handleUploadBookPdf(Long id);
}
