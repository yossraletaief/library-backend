package com.library.management.library_Backend.repository;

import com.library.management.library_Backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    // Search by title
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Search by author
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Search by title AND author
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);

    // Check if a book with the same title and author exists
    boolean existsByTitleAndAuthor(String title, String author);

    // Check if a book with the same ISBN exists
    boolean existsByIsbn(String isbn);
}
