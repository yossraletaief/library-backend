package com.library.management.library_Backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.library_Backend.dto.BookDto;
import com.library.management.library_Backend.entity.Book;
import com.library.management.library_Backend.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookServiceImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private BookRepository bookRepository;


    private Book testBook;
    private BookDto testBookDto;

    @BeforeEach
    void setUp() {
        testBook = new Book(1L, "Effective Java", "Joshua Bloch", "978-0134685991", 2008, "A guide to Java best practices.");
        testBookDto = new BookDto(1L, "Effective Java", "Joshua Bloch", "978-0134685991", 2008, "A guide to Java best practices.");
    }

    // ✅ 1. Test Creating a Book Successfully
    @Test
    void shouldCreateBookSuccessfully() throws Exception {
        when(bookRepository.existsByTitleAndAuthor(any(), any())).thenReturn(false);
        when(bookRepository.existsByIsbn(any())).thenReturn(false);
        when(bookRepository.save(any())).thenReturn(testBook);


    }


    // ❌ 3. Test Getting a Non-existent Book
    @Test
    void shouldReturnNotFoundWhenBookDoesNotExist() throws Exception {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/2"))
                .andExpect(status().isNotFound());
    }

    // ✅ 4. Test Deleting a Book Successfully
    @Test
    void shouldDeleteBookSuccessfully() throws Exception {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        doNothing().when(bookRepository).delete(testBook);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
    }

    // ❌ 5. Test Deleting a Non-existent Book
    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentBook() throws Exception {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/books/2"))
                .andExpect(status().isNotFound());
    }
}
