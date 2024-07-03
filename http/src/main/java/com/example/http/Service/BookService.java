package com.example.http.Service;

import com.example.http.payload.request.BookRequest;
import com.example.http.payload.response.BaseResponse;
import com.example.http.Model.Book;

import javax.servlet.http.HttpServletRequest;


public interface BookService {
    BaseResponse<?> addBook(BookRequest bookRequest);
    BaseResponse<?> addBookByModel(Book book);
    BaseResponse<?> getBooks(String title);
    BaseResponse<?> findAll();
    BaseResponse<?> findBooksByAuthor(HttpServletRequest request);
}
