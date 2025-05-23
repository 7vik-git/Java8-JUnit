package com.gevernova;

import static org.junit.jupiter.api.Assertions.*;

import com.gevernova.library.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class LibraryServiceTest {

    private LibraryService service;
    private User user;

    private Book book1, book2, book3, book4;

    @BeforeEach
    void setUp() {
        service = new LibraryService();
        user = new User("u1", "Test User");

        book1 = new Book("book1", "Java Programming", "Author A", "Programming");
        book2 = new Book("book2", "Python Basics", "Author B", "Programming");
        book3 = new Book("book3", "Mystery Tales", "Author C", "Fiction");
        book4 = new Book("book4", "History 101", "Author D", "History");

        service.addBook(book1);
        service.addBook(book2);
        service.addBook(book3);
        service.addBook(book4);
    }

    @Test
    void testBorrowAndReturnBook() throws Exception {
        service.borrowBook(user, "book1");
        assertTrue(book1.isBorrowed());
        assertEquals(1, user.getBorrowedBooks().size());

        service.returnBook(user, "book1");
        assertFalse(book1.isBorrowed());
        assertEquals(0, user.getBorrowedBooks().size());
    }

    @Test
    void testListAvailableAndBorrowedBooks() throws Exception {
        service.borrowBook(user, "book2");
        List<Book> available = service.listAvailableBooks();
        List<Book> borrowed = service.listBorrowedBooks();
        assertTrue(available.stream().noneMatch(b -> b.getId().equals("book2")));
        assertTrue(borrowed.stream().anyMatch(b -> b.getId().equals("book2")));
    }

    @Test
    void testFilterBooksByAuthor() {
        List<Book> filtered = service.filterBooksByAuthor("Author A");
        assertEquals(1, filtered.size());
        assertEquals("book1", filtered.get(0).getId());
    }

    @Test
    void testFilterBooksByGenre() {
        List<Book> filtered = service.filterBooksByGenre("Programming");
        assertEquals(2, filtered.size());
    }

    @Test
    void testBorrowUnavailableBookThrows() throws Exception {
        service.borrowBook(user, "book3");
        assertThrows(BookUnavailableException.class, () ->
                service.borrowBook(new User("u2", "Other"), "book3"));
    }

    @Test
    void testBorrowMoreThanLimitThrows() throws Exception {
        service.borrowBook(user, "book1");
        service.borrowBook(user, "book2");
        service.borrowBook(user, "book4");
        assertThrows(BookLimitExceededException.class, () ->
                service.borrowBook(user, "book3"));
    }

    @Test
    void testBorrowNonExistingBookThrows() {
        assertThrows(BookUnavailableException.class, () ->
                service.borrowBook(user, "b999"));
    }
}
