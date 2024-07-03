package com.example.http.repository;

import com.example.http.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT b FROM Book b WHERE b.title = :title")
    Optional<Book> findBookByTitle(@Param("title") String title);

    List<Book> findBooksByAuthor(String author);
}
