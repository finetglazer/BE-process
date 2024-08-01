package com.example.http.Service.impl;

import com.example.http.Helper.Pdf.CreatePdf;
import com.example.http.Helper.Word.CreateWord;
import com.example.http.Model.Book;
import com.example.http.Payload.response.BaseResponse;
import com.example.http.Repository.BookRepository;
import com.example.http.Service.ExportService;
import com.example.http.Service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private CreateWord createWord;

    @Autowired
    private CreatePdf createPdf;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UploadFileService uploadFileService;


    @Override
    public BaseResponse<?> handleExportWord() {
        try {
            createWord.convert(createWord.createRawWord());
            return new BaseResponse<>(1, createWord.createRawWord());
        } catch (Exception e) {
            return new BaseResponse<>(1, "Error when create word");
        }

    }

    @Override
    public BaseResponse<?> handleExportWordByFile() {
        try {
            File file = new File(createWord.createRawWordByFile());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return new BaseResponse<>(1, resource);
        } catch (Exception e) {
            return new BaseResponse<>(1, "Error when create word");
        }
    }

    @Override
    public BaseResponse<?> handleExportPdf() {
        try {
            return new BaseResponse<>(1, createPdf.createPdfString());
        } catch (Exception e) {
            return new BaseResponse<>(1, "Error when create pdf");
        }
    }

    @Override
    public BaseResponse<?> handleSaveWord(String encodedString) {
        try {
            createWord.convert(encodedString);
            return new BaseResponse<>(1, "Successfully save");
        } catch (Exception e) {
            return new BaseResponse<>(1, "Error when create word");
        }
    }

    @Override
    public BaseResponse<?> handleExportBookWord(Long id) {
        try {
            Book book = bookRepository.findById(id).orElse(null);
            if (book == null) {
                return new BaseResponse<>(1, "Book not found");
            }
            createWord.convert(createWord.createWordBySource(book));
            return new BaseResponse<>(1, "Successfully create book");
        } catch (Exception e) {
            return new BaseResponse<>(1, "Error when create book");
        }
    }

    @Override
    public BaseResponse<?> handleExportBookPdf(Long id) {
        try {
            Book book = bookRepository.findById(id).orElse(null);
            if (book == null) {
                return new BaseResponse<>(1, "Book not found");
            }
            createPdf.convert(createPdf.createPdfBySource(book));
            return new BaseResponse<>(1, "Successfully create book");
        } catch (Exception e) {
            return new BaseResponse<>(1, "Error when create book");
        }
    }

//    @Override
//    public BaseResponse<?> handleUploadBookPdf(Long id) {
//        try {
//            Book book = bookRepository.findById(id).orElse(null);
//            if (book == null) {
//                return new BaseResponse<>(1, "Book not found");
//            }
//            createPdf.convert(createPdf.createPdfBySource(book));
//
//            return new BaseResponse<>(1, "Successfully upload book");
//        } catch (Exception e) {
//            return new BaseResponse<>(1, "Error when upload book");
//        }
//    }



}
