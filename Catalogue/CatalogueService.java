package Catalogue;

import java.util.*;

public class CatalogueService implements ICatalogueService {
    private final CatalogueRepository catalogueRepository = new CatalogueRepository();
    private int nextId = 1;

    @Override
    public Book addBook(String title, String author, int totalCopies) {
        Book book = new Book(nextId++, title, author, totalCopies);
        catalogueRepository.save(book);
        return book;
    }

    @Override
    public List<Book> listBooks() {
        return catalogueRepository.findAll();
    }

    @Override
    public Book getBook(int bookId) {
        return catalogueRepository.findById(bookId);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return catalogueRepository.findAllByAuthor(author);
    }

    @Override
    public boolean isAvailable(int bookId) {
        Book book = catalogueRepository.findById(bookId);
        return book != null && book.getAvailableCopies() > 0;
    }

    // synchronize
    // Without synchronized, imagine a book with 1 copy left, and two users click
    // "borrow" at the same instant:
    // Thread A: reads availableCopies → sees 1
    // Thread B: reads availableCopies → sees 1 (A hasn't written yet)
    // Thread A: sets availableCopies = 0
    // Thread B: sets availableCopies = 0

    // With synchronized:
    // Thread A: enters method, locks
    // Thread B: tries to enter, BLOCKS and waits
    // Thread A: reads 1, checks >0 ✓, writes 0, unlocks
    // Thread B: now enters, locks
    // Thread B: reads 0, checks >0 ✗, throws IllegalStateException
    @Override
    public synchronized void decreaseAvailability(int bookId) {
        Book book = catalogueRepository.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found: " + bookId);
        }

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalArgumentException("No available copies for book: " + bookId);
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

    }

    @Override
    public synchronized void increaseAvailability(int bookId) {
        Book book = catalogueRepository.findById(bookId);

        if (book == null) {
            throw new IllegalArgumentException("Book not found: " + bookId);
        }

        if (book.getAvailableCopies() < book.getTotalCopies()) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
        }
    }

}
