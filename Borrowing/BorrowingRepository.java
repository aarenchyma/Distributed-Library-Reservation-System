package Borrowing;
import java.util.*;

public class BorrowingRepository {
    private final List<BorrowRecord> records = new ArrayList<>();

    void save(BorrowRecord record) {
        records.add(record);
    }

    BorrowRecord findActiveByUserAndBook(int userId, int bookId) {

        for (BorrowRecord record : records) {
            if (record.getUserId() == userId && record.getBookId() == bookId && record.isActive()) {
                return record;
            }
        }

        return null;
    }

    List<BorrowRecord> findActiveByUser(int userId) {
        List<BorrowRecord> result = new ArrayList<>();

        for (BorrowRecord record : records) {
            if ( record.getUserId() == userId && record.isActive()) {
                result.add(record);
            }
        }

        return result;
    }
    
}
