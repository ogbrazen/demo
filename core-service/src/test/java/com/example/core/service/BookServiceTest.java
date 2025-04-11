package com.example.core.service;

import com.example.core.entity.Book;
import com.example.core.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = List.of(new Book(1L, "Title", "Author", 2023));
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        Book book = new Book(1L, "Title", "Author", 2023);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals("Title", result.get().getTitle());
    }

    @Test
    void testCreateBook() {
        Book book = new Book(null, "New Book", "Author", 2024);
        Book savedBook = new Book(1L, "New Book", "Author", 2024);

        when(bookRepository.save(book)).thenReturn(savedBook);

        Book result = bookService.createBook(book);

        assertNotNull(result.getId());
        assertEquals("New Book", result.getTitle());
    }

    @Test
    void testUpdateBook() {
        Book existing = new Book(1L, "Old", "Old", 2020);
        Book updated = new Book(null, "New", "New", 2024);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Book> result = bookService.updateBook(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("New", result.get().getTitle());
    }

    @Test
    void testDeleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        boolean result = bookService.deleteBook(1L);

        assertTrue(result);
        verify(bookRepository).deleteById(1L);
    }

}
