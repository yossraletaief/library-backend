package com.library.management.library_Backend.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto-generated unique identifier

    @NotBlank
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title; // The title of the book

    @NotBlank
    @Size(min = 3, max = 50, message = "Author name must be between 3 and 50 characters")
    private String author; // The author of the book

    @NotBlank
    //@Pattern(regexp = "^[0-9]{13}$", message = "ISBN must be a 13-digit number")
    private String isbn; // The International Standard Book Number

    @Min(value = 1450, message = "Publication year must be after 1450")
    @Max(value = 2025, message = "Publication year must not exceed 2025")
    private int publicationYear; // The year the book was published

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description; // A brief summary of the book (for AI processing)
}
