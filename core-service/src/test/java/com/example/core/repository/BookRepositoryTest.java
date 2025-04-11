package com.example.core.repository;

import com.example.core.entity.Book;
import com.example.core.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Проверка книг из init.sql")
    void testFindAllBooks() {
        List<Book> books = bookRepository.findAll();
        assertEquals(3, books.size());

        assertTrue(books.stream().anyMatch(b -> b.getAuthor().equals("Алексей Варламов")));
    }
}
