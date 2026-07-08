package Catalogue;

import java.util.*;

public class CatalogueRepository {
    private final List<Book> books = new ArrayList<>();

    void save(Book book) {
        books.add(book);
    }

    Book findById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    List<Book> findAll() {
        return new ArrayList<>(books);
    }

    List<Book> findAllByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }
}
