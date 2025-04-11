package com.example.core.controller;

import com.example.core.controller.BookController;
import com.example.core.entity.Book;
import com.example.core.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllBooks() throws Exception {
        List<Book> books = List.of(new Book(1L, "Title", "Author", 2023));
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title"));
    }

    @Test
    void testGetBookByIdFound() throws Exception {
        Book book = new Book(1L, "Title", "Author", 2023);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    void testGetBookByIdNotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBook() throws Exception {
        Book book = new Book(null, "New", "Author", 2024);
        Book savedBook = new Book(1L, "New", "Author", 2024);

        when(bookService.createBook(any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateBookFound() throws Exception {
        Book updated = new Book(1L, "Updated", "Author", 2024);
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    void testUpdateBookNotFound() throws Exception {
        Book updated = new Book(null, "Updated", "Author", 2024);
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBookFound() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBookNotFound() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(false);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNotFound());
    }
}