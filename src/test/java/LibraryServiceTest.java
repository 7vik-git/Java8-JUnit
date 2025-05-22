import static org.junit.jupiter.api.Assertions.*;

import com.gevernova.library.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LibraryServiceTest {
    LibraryService service = new LibraryService();
    User user = new User("u1", "Test User");

    Book b1 = new Book("b1", "Java Programming", "Author A", "Programming");
    Book b2 = new Book("b2", "Python Basics", "Author B", "Programming");
    Book b3 = new Book("b3", "Mystery Tales", "Author C", "Fiction");
    Book b4 = new Book("b4", "History 101", "Author D", "History");

    public LibraryServiceTest() {
        service.addBook(b1);
        service.addBook(b2);
        service.addBook(b3);
        service.addBook(b4);
    }
    @Test
    void testBorrowAndReturnBook() throws Exception {
        service.borrowBook(user, "b1");
        assertTrue(b1.isBorrowed());
        assertEquals(1, user.getBorrowedBooks().size());

        service.returnBook(user, "b1");
        assertFalse(b1.isBorrowed());
        assertEquals(0, user.getBorrowedBooks().size());
    }

    @Test
    void testListAvailableAndBorrowedBooks() throws Exception {
        service.borrowBook(user, "b2");
        List<Book> available = service.listAvailableBooks();
        List<Book> borrowed = service.listBorrowedBooks();
        assertTrue(available.stream().noneMatch(b -> b.getId().equals("b2")));
        assertTrue(borrowed.stream().anyMatch(b -> b.getId().equals("b2")));
    }

    @Test
    void testFilterBooksByAuthor() {
        List<Book> filtered = service.filterBooksByAuthor("Author A");
        assertEquals(1, filtered.size());
        assertEquals("b1", filtered.get(0).getId());
    }

    @Test
    void testFilterBooksByGenre() {
        List<Book> filtered = service.filterBooksByGenre("Programming");
        assertEquals(2, filtered.size());
    }
//N
    @Test
    void testBorrowUnavailableBookThrows() throws Exception {
        service.borrowBook(user, "b3");
        assertThrows(BookUnavailableException.class, () -> service.borrowBook(new User("u2", "Other"), "b3"));
    }

    @Test
    void testBorrowMoreThanLimitThrows() throws Exception {
        service.borrowBook(user, "b1");
        service.borrowBook(user, "b2");
        service.borrowBook(user, "b4");
        assertThrows(BookLimitExceededException.class, () -> service.borrowBook(user, "b3"));
    }

    @Test
    void testBorrowNonExistingBookThrows() {
        assertThrows(BookUnavailableException.class, () -> service.borrowBook(user, "b999"));
    }
}
