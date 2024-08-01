package com.example.http.Service.impl;

import com.example.http.Model.Account;
import com.example.http.Model.Book;
import com.example.http.Model.User;
import com.example.http.Service.BookService;
import com.example.http.Payload.request.BookRequest;
import com.example.http.Payload.response.BaseResponse;
import com.example.http.Repository.UserRepository;
import com.example.http.Security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.http.Repository.BookRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    @Override
    public BaseResponse<?> addBook(BookRequest bookRequest) {
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .build();
        bookRepository.save(book);
        return new BaseResponse<>(book);
    }

    @Override
    public BaseResponse<?> addBookByModel(Book book) {
        bookRepository.save(book);
        return new BaseResponse<>(book);
    }

    @Override
    public BaseResponse<?> getBooks(String title) {
        return new BaseResponse<>(bookRepository.findBookByTitle(title));
    }

    @Override
    public BaseResponse<?> findAll() {
        return new BaseResponse<>(bookRepository.findAll());
    }

    @Override
    public BaseResponse<?> findBooksByAuthor(HttpServletRequest request) {
        Account account = jwtUtils.checkJwt(request);
        User user = userRepository.findUserByUsername(account.getUsername()).orElseThrow(()->new RuntimeException("User not found"));
        return new BaseResponse<>(bookRepository.findBooksByAuthor(user.getUsername()));

    }
}
