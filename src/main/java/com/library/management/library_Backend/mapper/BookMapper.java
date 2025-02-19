package com.library.management.library_Backend.mapper;

import com.library.management.library_Backend.dto.BookDto;
import com.library.management.library_Backend.entity.Book;

public class BookMapper {

    public static BookDto mapToBookDto(Book book){
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getDescription()
        );
    }


    public static Book mapToBook(BookDto bookDto){
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getIsbn(),
                bookDto.getPublicationYear(),
                bookDto.getDescription()
        );
    }

}
