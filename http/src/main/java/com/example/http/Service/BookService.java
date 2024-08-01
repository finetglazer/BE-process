package com.example.http.Service;

import com.example.http.Payload.request.BookRequest;
import com.example.http.Payload.response.BaseResponse;
import com.example.http.Model.Book;

import javax.servlet.http.HttpServletRequest;


public interface BookService {
    BaseResponse<?> addBook(BookRequest bookRequest);
    BaseResponse<?> addBookByModel(Book book);
    BaseResponse<?> getBooks(String title);
    BaseResponse<?> findAll();
    BaseResponse<?> findBooksByAuthor(HttpServletRequest request);
}
