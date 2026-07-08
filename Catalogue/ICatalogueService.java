package Catalogue;

import java.util.List;

public interface ICatalogueService {
    Book addBook(String title, String Author, int totalCopies);

    Book getBook(int BookId);
    List<Book> listBooks();
    List<Book> getBooksByAuthor(String author);

    boolean isAvailable(int bookId);

    // Called only by the Borrowing module — never mutate availability
    // from any other module directly, always go through here.
    void decreaseAvailability(int bookId);
    void increaseAvailability(int bookId);
}
