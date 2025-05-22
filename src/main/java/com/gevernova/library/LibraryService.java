package com.gevernova.library;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public Optional<Book> findBookById(String id) {
        return Optional.ofNullable(books.get(id));
    }

    public List<Book> listAvailableBooks() {
        return books.values().stream()
                .filter(b -> !b.isBorrowed())
                .collect(Collectors.toList());
    }

    public List<Book> listBorrowedBooks() {
        return books.values().stream()
                .filter(Book::isBorrowed)
                .collect(Collectors.toList());
    }

    public List<Book> filterBooksByAuthor(String author) {
        return books.values().stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> filterBooksByGenre(String genre) {
        return books.values().stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public void borrowBook(User user, String bookId) throws BookUnavailableException, BookLimitExceededException {
        if (user.getBorrowedBooks().size() >= 3) throw new BookLimitExceededException("User has reached book limit");
        Book book = books.get(bookId);
        if (book == null || book.isBorrowed()) throw new BookUnavailableException("Book is not available");
        book.setBorrowed(true);
        user.borrowBook(book);
    }

    public void returnBook(User user, String bookId) {
        books.get(bookId).setBorrowed(false);
        user.getBorrowedBooks().removeIf(b -> b.getId().equals(bookId));
    }
}

