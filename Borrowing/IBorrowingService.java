package Borrowing;
import java.util.List;

public interface IBorrowingService {
    BorrowRecord borrowBook(int userId, int bookId);
    void returnBook(int userId, int bookId);
    List<BorrowRecord> listActiveBorrowsByUser(int userId);
}
