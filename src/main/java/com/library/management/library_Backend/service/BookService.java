package com.library.management.library_Backend.service;

import com.library.management.library_Backend.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook( BookDto bookDto);

    BookDto getBookById(Long bookId);


    List<BookDto> getAllBooks();

    BookDto updateBook(Long bookId, BookDto bookDto);

    String getBookAIInsights(Long bookId); // New method

    List<BookDto> searchBooks(String title, String author);
    void deleteBook(Long bookId);

}
