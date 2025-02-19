package com.library.management.library_Backend.service.impl;

import com.library.management.library_Backend.dto.BookDto;
import com.library.management.library_Backend.entity.Book;
import com.library.management.library_Backend.exception.ResourceNotFoundException;
import com.library.management.library_Backend.mapper.BookMapper;
import com.library.management.library_Backend.repository.BookRepository;
import com.library.management.library_Backend.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final RestTemplate restTemplate= new RestTemplate();
     ;
    private static final String AI_SERVICE_URL = "https://api.openai.com/v1/chat/completions";

    private static final String AI_API_KEY = "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"; // Replace with your actual key

    private BookRepository bookRepository;
    @Override
    public BookDto createBook(BookDto bookDto) {
        // Check if a book with the same title and author already exists
        boolean bookExists = bookRepository.existsByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
        if (bookExists) {
            throw new IllegalArgumentException("A book with the same title and author already exists.");
        }

        // Check if the ISBN is already taken
        boolean isbnExists = bookRepository.existsByIsbn(bookDto.getIsbn());
        if (isbnExists) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }

        // Convert DTO to entity and save the new book
        Book book = BookMapper.mapToBook(bookDto);
        Book savedBook = bookRepository.save(book);

        // Return the saved book as a DTO
        return BookMapper.mapToBookDto(savedBook);
    }

    @Override
    public BookDto getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book does not exist"));
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(Long bookId, BookDto bookDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book does not exist"));

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());

        Book updatedBook = bookRepository.save(book);
        return BookMapper.mapToBookDto(updatedBook);
    }
    @Override
    public List<BookDto> searchBooks(String title, String author) {
        List<Book> books;
        if (title != null && author != null) {
            books = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
        } else if (title != null) {
            books = bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            books = bookRepository.findByAuthorContainingIgnoreCase(author);
        } else {
            books = bookRepository.findAll();
        }

        return books.stream()
                .map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor(),book.getIsbn(),book.getPublicationYear(), book.getDescription()))
                .collect(Collectors.toList());
    }
    @Override
    public String getBookAIInsights(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book does not exist"));

        // Build prompt for AI
        String prompt = String.format("Generate a short, engaging tagline or summary for the book titled '%s' ",
                book.getTitle());

        // Prepare request payload
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o");

        // OpenAI's chat model requires a list of messages
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a helpful AI assistant."));
        messages.add(Map.of("role", "user", "content", prompt));

        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 50);

        // Set headers
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + AI_API_KEY);
        headers.set("Content-Type", "application/json");

        // Make API call
        org.springframework.http.HttpEntity<Map<String, Object>> request = new org.springframework.http.HttpEntity<>(requestBody, headers);
        Map<String, Object> response = restTemplate.postForObject(AI_SERVICE_URL, request, Map.class);

        // Debugging: Print the response to check its structure
        System.out.println("AI Response: " + response);

        // Check if response is null
        if (response == null || !response.containsKey("choices")) {
            throw new RuntimeException("Invalid AI response: missing 'choices'");
        }

        // Extract choices
        List<?> choices = (List<?>) response.get("choices");
        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("AI response contains no choices");
        }

        // Extract first choice safely
        Object firstChoice = choices.get(0);
        if (!(firstChoice instanceof Map)) {
            throw new RuntimeException("Unexpected AI response format");
        }

        Map<String, Object> choiceMap = (Map<String, Object>) firstChoice;
        Object messageObject = choiceMap.get("message");
        if (!(messageObject instanceof Map)) {
            throw new RuntimeException("AI response missing 'message' key");
        }

        Map<String, Object> messageMap = (Map<String, Object>) messageObject;
        Object content = messageMap.get("content");

        if (content == null) {
            throw new RuntimeException("AI response missing 'content'");
        }

        return content.toString().trim();
    }


    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book does not exist"));
        bookRepository.delete(book);
    }
}
