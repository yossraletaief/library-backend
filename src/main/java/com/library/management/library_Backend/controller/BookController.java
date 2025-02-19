package com.library.management.library_Backend.controller;

import com.library.management.library_Backend.dto.BookDto;
import com.library.management.library_Backend.service.BookService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    private BookService bookService;

    //Build ADD Book Rest API
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto){
        BookDto savedBook=bookService.createBook(bookDto);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
    // Build GET Book by ID API
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long bookId) {
        BookDto bookDto = bookService.getBookById(bookId);
        return ResponseEntity.ok(bookDto);
    }

    // Build GET All Books API
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // Build UPDATE Book API
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long bookId, @RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.updateBook(bookId, bookDto);
        return ResponseEntity.ok(updatedBook);
    }

    // Build DELETE Book API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok("Book deleted successfully");
    }

    @GetMapping("/{id}/ai-insights")
    public ResponseEntity<String> getBookAIInsights(@PathVariable("id") Long bookId) {
        String insights = bookService.getBookAIInsights(bookId);
        return ResponseEntity.ok(insights);
    }


    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {

        List<BookDto> books = bookService.searchBooks(title, author);
        return ResponseEntity.ok(books);
    }
}
