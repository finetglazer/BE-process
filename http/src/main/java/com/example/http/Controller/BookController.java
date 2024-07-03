package com.example.http.Controller;

import com.example.http.Model.Book;
import com.example.http.Service.BookService;
import com.example.http.payload.request.BookRequest;
import com.example.http.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @PreAuthorize("hasAuthority('book_write')")
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody @Valid BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.addBook(bookRequest));
    }

    @PreAuthorize("hasAuthority('book_write')")
    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@ModelAttribute("book") Book book) {
        return ResponseEntity.ok(bookService.addBookByModel(book));
    }

    @PreAuthorize("hasAuthority('book_read')")
    @GetMapping("/getBookByTitle/{title}")
    public ResponseEntity<?> getBook(@PathVariable(name = "title") String title) {
        return ResponseEntity.ok(bookService.getBooks(title));
    }

    @PreAuthorize("hasAuthority('book_read')")
    @GetMapping("/getBookById/{id}")
    public ResponseEntity<?> getBookById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(bookRepository.findById(id));
    }
    @PreAuthorize("hasAuthority('book_read')")
    @GetMapping("/getAllBooks")
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/getAllBooksOfMe")
    public ResponseEntity<?> getAllBooksOfMe(HttpServletRequest request) {
        return ResponseEntity.ok(bookService.findBooksByAuthor(request));
    }
}
