package Borrowing;
import java.time.LocalDateTime;

public class BorrowRecord {
    private final int id;
    private final int userId;
    private final int bookId;
    private final LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;

    public BorrowRecord(int id, int userId, int bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = userId;
        this.borrowedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public boolean isActive() {
        return returnedAt == null;
    }

    void markReturned() {
        this.returnedAt = LocalDateTime.now();
    }

}
